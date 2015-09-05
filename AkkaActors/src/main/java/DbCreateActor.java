import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.rashwan.akka.messages.UserMessage;
import org.reactivecouchbase.ReactiveCouchbaseDriver;
import org.reactivecouchbase.client.OpResult;
import org.reactivecouchbase.common.Functionnal;
import org.reactivecouchbase.japi.CouchbaseBucket;
import org.reactivecouchbase.japi.FormatHelper;
import org.reactivecouchbase.json.JsObject;
import org.reactivecouchbase.json.Json;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.reactivecouchbase.json.Syntax.$;

/**
 * Created by rashwan on 9/4/15.
 */
public class DbCreateActor  extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(context().system(), this);
    final ReactiveCouchbaseDriver driver = ReactiveCouchbaseDriver.apply();
    final CouchbaseBucket bucket = new CouchbaseBucket(driver.bucket("newBucket"));
    final ExecutorService executorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        log.debug("DB actor created");
        return ReceiveBuilder
                .match(UserMessage.class, receivedMessage -> {
                    final ActorRef sender = getContext().sender();
                    log.debug("received UserMessage in Actor");
                    JsObject object = Json.obj(
                            $("name", receivedMessage.getName()),
                            $("job", receivedMessage.getJob()),
                            $("email", receivedMessage.getEmail())

                    );
                    Random id = new Random();
                    bucket.set(String.valueOf(id.nextInt()), object, FormatHelper.JS_OBJECT_FORMAT)
                            .onSuccess(new Functionnal.Action<OpResult>() {
                                @Override
                                public void call(OpResult opResult) {
                                    sender.tell("added user With Name: " + receivedMessage.getName() + " to the DB", self());
                                }
                            }, executorService);


                })
                .matchAny(o -> log.info("Unknown Message Type")).build();
    }
}
