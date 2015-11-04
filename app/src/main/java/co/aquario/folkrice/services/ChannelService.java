package co.aquario.folkrice.services;

import java.util.ArrayList;
import java.util.List;

import co.aquario.folkrice.model.PostDataNew;

import rx.Observable;

public class ChannelService {
    public Observable<List<PostDataNew>> getList() {
        List<PostDataNew> channels = new ArrayList<PostDataNew>() {{

        }};
        return Observable.just(channels);
    }
}
