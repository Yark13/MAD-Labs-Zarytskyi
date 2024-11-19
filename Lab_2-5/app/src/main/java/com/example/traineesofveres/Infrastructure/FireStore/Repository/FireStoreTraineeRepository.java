package com.example.traineesofveres.Infrastructure.FireStore.Repository;

import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.traineesofveres.DTO.Infrastructure.Trainee;
import com.example.traineesofveres.Domain.DALInterfaces.IRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
        Task<QuerySnapshot> task = _collection.get();
        task.addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                Trainee trainee = doc.toObject(Trainee.class);
                trainee.Id = Integer.parseInt(doc.getId());
                trainees.add(trainee);
            }
        });
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
        _collection.limit(take)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Trainee trainee = doc.toObject(Trainee.class);
                        trainees.add(trainee);
                    }
                });
        return trainees;
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
        Task<QuerySnapshot> task = _collection.whereEqualTo("id", id).get();
        final Trainee[] result = new Trainee[1];
        task.addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                result[0] = doc.toObject(Trainee.class);
                result[0].Id = id;
            }
        });
        return result[0];
    }

    @Override
    public Trainee Add(Trainee entity) {
        Map<String, Object> doc = new HashMap<>();
        doc.put("id", entity.Id);
        doc.put("name", entity.Name);
        doc.put("surname", entity.Surname);
        doc.put("email", entity.Email);
        doc.put("password", entity.Password);
        doc.put("age", entity.Age);
        doc.put("score", entity.Score);

        DocumentReference newTrainee = _collection.document();

        newTrainee.set(doc)
                .addOnSuccessListener(aVoid -> Log.d("My firestore tags", "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w("My firestore tags", "Error writing document", e));
        //_collection.add(entity);
        return entity;
    }

    @Override
    public Trainee Update(Trainee entity) {
        _collection.document(String.valueOf(entity.Id)).set(entity);
        return entity;
    }
}
