package EjemploActores;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

public class SaludoBot extends AbstractBehavior<Saludo.Greeted> {

    public static Behavior<Saludo.Greeted> create(int max) {
        return Behaviors.setup(context -> new SaludoBot(context, max));
    }

    private final int max;
    private int saludoCount;

    private SaludoBot(ActorContext<Saludo.Greeted> context, int max) {
        super(context);
        this.max = max;
    }

    @Override
    public Receive<Saludo.Greeted> createReceive() {
        return newReceiveBuilder().onMessage(Saludo.Greeted.class, this::onGreeted).build();
    }

    private Behavior<Saludo.Greeted> onGreeted(Saludo.Greeted message) {
        saludoCount++;
        getContext().getLog().info("Registro {} para {}", saludoCount, message.sujeto);
        if (saludoCount == max) {
            return Behaviors.stopped();
        } else {
            message.emisor.tell(new Saludo.Greet(message.sujeto, getContext().getSelf()));

            return this;
        }
    }
}
