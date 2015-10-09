package co.aquario.mvp.services;

import java.util.ArrayList;
import java.util.List;

import co.aquario.mvp.model.PostDataNew;

import rx.Observable;

public class ChannelService {
    public Observable<List<PostDataNew>> getList() {
        List<PostDataNew> channels = new ArrayList<PostDataNew>() {{

        }};
        return Observable.just(channels);
    }
}
