package dungeonmania.response.models;

import lombok.Getter;

import java.util.List;

@Getter
public class AnimationQueue {
    private final String when;
    private final String entityId;
    private final List<String> queue;
    private final boolean loop;
    private final double duration;

    public AnimationQueue(String when, String entityId, List<String> queue, boolean loop, double duration) {
        this.when = when;
        this.entityId = entityId;
        this.queue = queue;
        this.loop = loop;
        this.duration = duration;
    }

}
