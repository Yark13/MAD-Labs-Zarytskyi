package com.example.traineesofveres.Infrastructure.FireStore.Repository;

import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.traineesofveres.DTO.Infrastructure.Trainee;
import com.example.traineesofveres.Domain.DALInterfaces.IRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Predicate;

import kotlin.Pair;

public class FireStoreTraineeRepository extends FireStoreRepository<Trainee> implements IRepository<Trainee> {
    private static final String _collectionPath = "trainees";

    public FireStoreTraineeRepository(FirebaseFirestore firestore) {
        super(firestore, _collectionPath);
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
        return new ArrayList<>(GetAll().subList(skip, skip+take));
    }

    @Override
    public ArrayList<Pair<Trainee, Integer>> GetTopWithRank(int topCount, int traineeId) {
        ArrayList<Pair<Trainee, Integer>> rankedTrainees = new ArrayList<>();
        _collection.orderBy("rank")
                .limit(topCount)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int rank = 1;
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Trainee trainee = doc.toObject(Trainee.class);
                        rankedTrainees.add(new Pair<>(trainee, rank++));
                    }
                });
        return rankedTrainees;
    }

    @Override
    public Trainee Find(int id) {
        final Trainee[] foundTrainee = {null};

        Thread thread = new Thread(() -> {
            try {
                Query query = _collection.whereEqualTo("Id", id);
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
        entity.Id = Trainee.GetNewId();
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
}
