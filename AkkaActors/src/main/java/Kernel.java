import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.kernel.Bootable;

/**
 * Created by rashwan on 9/4/15.
 */
public class Kernel implements Bootable {

    final ActorSystem system = ActorSystem.create("Kernel");
    @Override
    public void startup() {
        system.actorOf(Props.create(DbCreateActor.class),"DBActor");
    }

    @Override
    public void shutdown() {
        system.shutdown();
    }
}
