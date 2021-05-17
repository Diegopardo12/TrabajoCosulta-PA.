package EjemploActores;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

// #greeter
public class Saludo extends AbstractBehavior<Saludo.Greet> {

  public static final class Greet {
    public final String sujeto;
    public final ActorRef<Greeted> rpta;

    public Greet(String sujeto, ActorRef<Greeted> rpta) {
      this.sujeto = sujeto;
      this.rpta = rpta;
    }
  }

  public static final class Greeted {
    public final String sujeto;
    public final ActorRef<Greet> emisor;

    public Greeted(String sujeto, ActorRef<Greet> emisor) {
      this.sujeto = sujeto;
      this.emisor = emisor;
    }
  }

  public static Behavior<Greet> create() {
    return Behaviors.setup(Saludo::new);
  }

  private Saludo(ActorContext<Greet> context) {
    super(context);
  }

  @Override
  public Receive<Greet> createReceive() {
    return newReceiveBuilder().onMessage(Greet.class, this::onGreet).build();
  }

  private Behavior<Greet> onGreet(Greet command) {
    getContext().getLog().info("Bienvenido {}!", command.sujeto);
    //#greeter-send-message
    command.rpta.tell(new Greeted(command.sujeto, getContext().getSelf()));
    //#greeter-send-message
    return this;
  }
}


