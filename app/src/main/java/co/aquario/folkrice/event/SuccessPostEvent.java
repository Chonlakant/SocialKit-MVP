package co.aquario.folkrice.event;

import co.aquario.folkrice.model.PostData;

/**
 * Created by root1 on 10/5/15.
 */
public class SuccessPostEvent {
    PostData postEvent;

    public SuccessPostEvent(PostData postEvent) {
        this.postEvent = postEvent;
    }

    public PostData getPostEvent() {
        return postEvent;
    }
}
