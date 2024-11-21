package com.example.traineesofveres.Infrastructure.FireStore.Repository;

import android.util.Log;

import com.example.traineesofveres.DTO.Infrastructure.Quote;
import com.example.traineesofveres.DTO.Infrastructure.Trainee;
import com.example.traineesofveres.Domain.DALInterfaces.IRepository;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;

import kotlin.Pair;

public class FireStoreQuoteRepository  extends FireStoreRepository<Quote> implements IRepository<Quote> {
    private static final String _collectionPath = "quotes";

    private final String _quotesIdField = "Id";

    private int _quotesId = 0;

    public FireStoreQuoteRepository(FirebaseFirestore firestore) {
        super(firestore, _collectionPath);
        _quotesId = GetLastId();
    }

    @Override
    public ArrayList<Quote> GetAll() {
        ArrayList<Quote> quotes = new ArrayList<>();

        Thread thread = new Thread(() -> {
            try {
                QuerySnapshot querySnapshot = Tasks.await(_collection.get());
                for (QueryDocumentSnapshot document : querySnapshot) {
                    Quote quote = document.toObject(Quote.class);
                    quotes.add(quote);
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

        return quotes;
    }

    @Override
    public ArrayList<Quote> GetAll(Predicate<Quote> filter) {
        ArrayList<Quote> allQuotes = GetAll();
        ArrayList<Quote> filteredQuotes = new ArrayList<>();
        for (Quote trainee : allQuotes) {
            if (filter.test(trainee)) {
                filteredQuotes.add(trainee);
            }
        }
        return filteredQuotes;
    }

    @Override
    public ArrayList<Quote> GetAll(int skip, int take) {
        ArrayList<Quote> quotes = new ArrayList<>();

        Thread thread = new Thread(() -> {
            try {
                Query query = _collection.orderBy(_quotesIdField).startAt(skip).limit(take);
                QuerySnapshot querySnapshot = Tasks.await(query.get());

                if (!querySnapshot.isEmpty()) {
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        Quote trainee = document.toObject(Quote.class);
                        quotes.add(trainee);
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

        return quotes;
    }

    @Override
    public ArrayList<Pair<Quote, Integer>> GetTopWithRank(int topCount, int traineeId) {
        return new ArrayList<>();
    }

    @Override
    public Quote Find(int id) {
        final Quote[] foundQuote = {null};

        Thread thread = new Thread(() -> {
            try {
                Query query = _collection.whereEqualTo(_quotesIdField, id);
                QuerySnapshot querySnapshot = Tasks.await(query.get());

                if (!querySnapshot.isEmpty()) {
                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                    foundQuote[0] = document.toObject(Quote.class);
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

        return foundQuote[0];
    }

    @Override
    public Quote Add(Quote entity) {
        entity.Id = GetNewId();
        DocumentReference newTrainee = _collection.document(Integer.toString(entity.Id));

        newTrainee.set(entity)
                .addOnSuccessListener(aVoid -> Log.d("My firestore tags", "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w("My firestore tags", "Error writing document", e));
        return entity;
    }

    @Override
    public Quote Update(Quote entity) {
        _collection.document(String.valueOf(entity.Id)).set(entity);
        return entity;
    }

    private int GetLastId(){
        final int[] lastId = {0};

        Thread thread = new Thread(() -> {
            try {
                Query query = _collection
                        .orderBy(_quotesIdField, Query.Direction.DESCENDING)
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
        _quotesId++;
        return _quotesId;
    }
}
