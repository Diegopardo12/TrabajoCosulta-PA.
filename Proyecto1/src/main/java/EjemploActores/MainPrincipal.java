package EjemploActores;

import akka.actor.typed.ActorSystem;

public class MainPrincipal {
  public static void main(String[] args) {
    //#actor-system
    final ActorSystem<SaludoEjecutor.SayHello> greeterMain = ActorSystem.create(SaludoEjecutor.create(), "helloakka");
    //#actor-system

    //#main-send-messages
    greeterMain.tell(new SaludoEjecutor.SayHello("Carlos"));
    //#main-send-messages
    greeterMain.terminate();
  }
}

