package EjemploActores;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

public class SaludoEjecutor extends AbstractBehavior<SaludoEjecutor.SayHello> {

    public static class SayHello {
        public final String nombre;

        public SayHello(String nombre) {
            this.nombre = nombre;
        }
    }

    private final ActorRef<Saludo.Greet> greeter;

    public static Behavior<SayHello> create() {
        return Behaviors.setup(SaludoEjecutor::new);
    }

    private SaludoEjecutor(ActorContext<SayHello> context) {
        super(context);
        //#create-actors
        greeter = context.spawn(Saludo.create(), "greeter");
        //#create-actors
    }

    @Override
    public Receive<SayHello> createReceive() {
        return newReceiveBuilder().onMessage(SayHello.class, this::onSayHello).build();
    }

    private Behavior<SayHello> onSayHello(SayHello command) {
        //#create-actors
        ActorRef<Saludo.Greeted> rpta =
                getContext().spawn(SaludoBot.create(4), command.nombre);
        greeter.tell(new Saludo.Greet(command.nombre, rpta));
        //#create-actors
        return this;
    }
}
