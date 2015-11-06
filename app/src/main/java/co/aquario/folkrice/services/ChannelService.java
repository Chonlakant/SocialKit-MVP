package co.aquario.folkrice.services;

import java.util.ArrayList;
import java.util.List;

import co.aquario.folkrice.model.Product;

import rx.Observable;

public class ChannelService {
    public Observable<List<Product>> getList() {
        List<Product> channels = new ArrayList<Product>() {{

        }};
        return Observable.just(channels);
    }
}
