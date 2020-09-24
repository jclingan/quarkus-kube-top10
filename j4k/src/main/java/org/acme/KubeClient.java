package org.acme;

import io.fabric8.kubernetes.api.model.ListOptions;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watch;
import io.fabric8.kubernetes.client.Watcher;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.builders.EmitterBasedMulti;
import io.smallrye.mutiny.subscription.MultiEmitter;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/kube")
public class KubeClient {

    @Inject
    KubernetesClient client;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/pods")
    public List<String> getPods() {
        ListOptions options = new ListOptions();
        options.setLabelSelector("app.kubernetes.io/name=quarkus-kubernetes-client");
        List<String> hostnames = client.pods().list(/*options*/).getItems()
                .stream()
                .map(item -> item.getMetadata().getName())
                .collect(Collectors.toList());

        return hostnames;
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.TEXT_PLAIN)
    @Path("/pods/stream")
    public Publisher<String> getPodsMulti() {

        Multi<String> multi = Multi.createFrom().emitter(emitter -> {
            Watch watch = client.pods().watch(new Watcher<Pod>() {
                @Override
                public void eventReceived(Action action, Pod resource) {
                    emitter.emit(action.toString() + ": " + resource.getMetadata().getName());
                }

                @Override
                public void onClose(KubernetesClientException cause) {

                }
            });
        });

        return multi;
    }
}