package com.example.traineesofveres.Infrastructure.FireStore.Repository;

import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.traineesofveres.DTO.Infrastructure.Trainee;
import com.example.traineesofveres.Domain.DALInterfaces.IRepository;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;

import kotlin.Pair;

public class FireStoreTraineeRepository extends FireStoreRepository<Trainee> implements IRepository<Trainee> {
    private static final String _collectionPath = "trainees";
    private int _traineesId = 0;

    private final String _traineeIdField = "Id";
    private final String _traineeScoreField = "Score";

    public FireStoreTraineeRepository(FirebaseFirestore firestore) {
        super(firestore, _collectionPath);
        _traineesId = GetLastId();
    }

    @Override
    public ArrayList<Trainee> GetAll() {
        ArrayList<Trainee> trainees = new ArrayList<>();

        Thread thread = new Thread(() -> {
            try {
                QuerySnapshot querySnapshot = Tasks.await(_collection.get());
                for (QueryDocumentSnapshot document : querySnapshot) {
                    Trainee trainee = document.toObject(Trainee.class);
                    trainees.add(trainee);
                }
                Log.d("Firestore logs", "Successfully fetched trainees.");
            } catch (ExecutionException e) {
                Log.e("Firestore logs", "ExecutionException: Failed to fetch data", e);
            } catch (InterruptedException e) {
                Log.e("Firestore logs", "InterruptedException: Task was interrupted", e);
                Thread.currentThread().interrupt();
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("Firestore logs", "Main thread interrupted while waiting", e);
            Thread.currentThread().interrupt();
        }

        return trainees;
    }

    @Override
    public ArrayList<Trainee> GetAll(Predicate<Trainee> filter) {
        ArrayList<Trainee> allTrainees = GetAll();
        ArrayList<Trainee> filteredTrainees = new ArrayList<>();
        for (Trainee trainee : allTrainees) {
            if (filter.test(trainee)) {
                filteredTrainees.add(trainee);
            }
        }
        return filteredTrainees;
    }

    @Override
    public ArrayList<Trainee> GetAll(int skip, int take) {
        ArrayList<Trainee> trainees = new ArrayList<>();

        Thread thread = new Thread(() -> {
            try {
                Query query = _collection.startAfter(skip).limit(take);
                QuerySnapshot querySnapshot = Tasks.await(query.get());

                if (!querySnapshot.isEmpty()) {
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        Trainee trainee = document.toObject(Trainee.class);
                        trainees.add(trainee);
                    }
                    Log.d("Firestore logs", "Trainees from " + skip + " to " + skip+take + " found.");
                } else {
                    Log.d("Firestore logs", "No Trainee founds ");
                }
            } catch (ExecutionException e) {
                Log.e("Firestore logs", "ExecutionException: Failed to fetch Trainee", e);
            } catch (InterruptedException e) {
                Log.e("Firestore logs", "InterruptedException: Task was interrupted", e);
                Thread.currentThread().interrupt();
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("Firestore logs", "Main thread interrupted while waiting", e);
            Thread.currentThread().interrupt();
        }

        return trainees;
    }

    @Override
    public ArrayList<Pair<Trainee, Integer>> GetTopWithRank(int topCount, int traineeId) {
        ArrayList<Pair<Trainee, Integer>> trainees = new ArrayList<>();

        Thread thread = new Thread(() -> {
            try {
                Query query = _collection.orderBy(_traineeScoreField, Query.Direction.DESCENDING);
                QuerySnapshot querySnapshot = Tasks.await(query.get());

                if (!querySnapshot.isEmpty()) {
                    List<DocumentSnapshot> docs = querySnapshot.getDocuments();
                    for (int i = 0; i < Math.min(docs.size(), topCount); i++) {
                        Trainee trainee = docs.get(i).toObject(Trainee.class);
                        trainees.add(new Pair<>(trainee, i+1));
                    }

                    DocumentSnapshot traineeDoc = docs.stream().filter(d -> d.getId().equals(Integer.toString(traineeId))).findFirst().orElse(null);

                    trainees.add(new Pair<>(traineeDoc.toObject(Trainee.class), docs.indexOf(traineeDoc)+1));
                    Log.d("Firestore logs", "Ranked trainees from got found.");
                } else {
                    Log.d("Firestore logs", "No Trainee founds ");
                }
            } catch (ExecutionException e) {
                Log.e("Firestore logs", "ExecutionException: Failed to fetch ranked Trainees", e);
            } catch (InterruptedException e) {
                Log.e("Firestore logs", "InterruptedException: Task was interrupted", e);
                Thread.currentThread().interrupt();
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("Firestore logs", "Main thread interrupted while waiting", e);
            Thread.currentThread().interrupt();
        }

        return trainees;
    }

    @Override
    public Trainee Find(int id) {
        final Trainee[] foundTrainee = {null};

        Thread thread = new Thread(() -> {
            try {
                Query query = _collection.whereEqualTo(_traineeIdField, id);
                QuerySnapshot querySnapshot = Tasks.await(query.get());

                if (!querySnapshot.isEmpty()) {
                    DocumentSnapshot document = querySnapshot.getDocuments().get(0); // Assuming `id` is unique
                    foundTrainee[0] = document.toObject(Trainee.class);
                    Log.d("Firestore logs", "Trainee with id " + id + " found.");
                } else {
                    Log.d("Firestore logs", "No Trainee found with id " + id);
                }
            } catch (ExecutionException e) {
                Log.e("Firestore logs", "ExecutionException: Failed to fetch Trainee", e);
            } catch (InterruptedException e) {
                Log.e("Firestore logs", "InterruptedException: Task was interrupted", e);
                Thread.currentThread().interrupt();
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("Firestore logs", "Main thread interrupted while waiting", e);
            Thread.currentThread().interrupt();
        }

        return foundTrainee[0];
    }

    @Override
    public Trainee Add(Trainee entity) {
        entity.Id = GetNewId();
        DocumentReference newTrainee = _collection.document(Integer.toString(entity.Id));

        newTrainee.set(entity)
                .addOnSuccessListener(aVoid -> Log.d("My firestore tags", "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w("My firestore tags", "Error writing document", e));
        return entity;
    }

    @Override
    public Trainee Update(Trainee entity) {
        _collection.document(String.valueOf(entity.Id)).set(entity);
        return entity;
    }

    private int GetLastId(){
        final int[] lastId = {0};

        Thread thread = new Thread(() -> {
            try {
                Query query = _collection
                        .orderBy(_traineeIdField, Query.Direction.DESCENDING)
                        .limit(1);

                QuerySnapshot snapshot = Tasks.await(query.get());

                lastId[0] = Integer.parseInt(snapshot.getDocuments().get(0).getId());

            }
            catch (Exception e){

            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("Firestore logs", "Main thread interrupted while waiting", e);
            Thread.currentThread().interrupt();
        }

        return lastId[0];
    }

    private int GetNewId(){
        _traineesId++;
        return _traineesId;
    }
}
