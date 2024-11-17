package com.example.traineesofveres.Infrastructure.FireStore.Repository;

import com.example.traineesofveres.DTO.Infrastructure.Quote;
import com.example.traineesofveres.DTO.Infrastructure.Trainee;
import com.example.traineesofveres.Domain.DALInterfaces.IRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.function.Predicate;

import kotlin.Pair;

public class FireStoreQuoteRepository  extends FireStoreRepository<Quote> implements IRepository<Quote> {
    private static final String _collectionPath = "quote";

    public FireStoreQuoteRepository(FirebaseFirestore firestore) {
        super(firestore, _collectionPath);
    }

    @Override
    public ArrayList<Quote> GetAll() {
        ArrayList<Quote> quotes = new ArrayList<>();
        Task<QuerySnapshot> task = _collection.get();
        task.addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                Quote quote = doc.toObject(Quote.class);
                quote.Id = Integer.parseInt(doc.getId());
                quotes.add(quote);
            }
        });
        return quotes;
    }

    @Override
    public ArrayList<Quote> GetAll(Predicate<Quote> filter) {
        ArrayList<Quote> allQuotes = GetAll();
        ArrayList<Quote> filteredQuotes = new ArrayList<>();
        for (Quote quote : allQuotes) {
            if (filter.test(quote)) {
                filteredQuotes.add(quote);
            }
        }
        return filteredQuotes;
    }

    @Override
    public ArrayList<Quote> GetAll(int skip, int take) {
        ArrayList<Quote> quotes = new ArrayList<>();
        _collection.limit(take)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Quote quote = doc.toObject(Quote.class);
                        quotes.add(quote);
                    }
                });
        return quotes;
    }

    @Override
    public ArrayList<Pair<Quote, Integer>> GetTopWithRank(int topCount, int traineeId) {
        ArrayList<Pair<Quote, Integer>> rankedQuotes = new ArrayList<>();
        _collection.orderBy("rank")
                .limit(topCount)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int rank = 1;
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Quote quote = doc.toObject(Quote.class);
                        rankedQuotes.add(new Pair<>(quote, rank++));
                    }
                });
        return rankedQuotes;
    }

    @Override
    public Quote Find(int id) {
        Task<QuerySnapshot> task = _collection.whereEqualTo("id", id).get();
        final Quote[] result = new Quote[1];
        task.addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                result[0] = doc.toObject(Quote.class);
                result[0].Id = id;
            }
        });
        return result[0];
    }

    @Override
    public Quote Add(Quote entity) {
        _collection.add(entity);
        return entity;
    }

    @Override
    public Quote Update(Quote entity) {
        _collection.document(String.valueOf(entity.Id)).set(entity);
        return entity;
    }
}
