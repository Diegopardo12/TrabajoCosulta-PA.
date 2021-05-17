package example;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

public class GreeterBot extends AbstractBehavior<Greeter.Greeted> {

    public static Behavior<Greeter.Greeted> create(int max) {
        return Behaviors.setup(context -> new GreeterBot(context, max));
    }

    private final int max;
    private int saludoCount;

    private GreeterBot(ActorContext<Greeter.Greeted> context, int max) {
        super(context);
        this.max = max;
    }

    @Override
    public Receive<Greeter.Greeted> createReceive() {
        return newReceiveBuilder().onMessage(Greeter.Greeted.class, this::onGreeted).build();
    }

    private Behavior<Greeter.Greeted> onGreeted(Greeter.Greeted message) {
        saludoCount++;
        getContext().getLog().info("Registro {} para {}", saludoCount, message.sujeto);
        if (saludoCount == max) {
            return Behaviors.stopped();
        } else {
            message.emisor.tell(new Greeter.Greet(message.sujeto, getContext().getSelf()));

            return this;
        }
    }
}
