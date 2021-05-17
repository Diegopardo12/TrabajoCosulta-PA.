package example;

import akka.actor.typed.ActorSystem;

public class AkkaQuickstart {
  public static void main(String[] args) {
    //#actor-system
    final ActorSystem<GreeterMain.SayHello> greeterMain = ActorSystem.create(GreeterMain.create(), "helloakka");
    //#actor-system

    //#main-send-messages
    greeterMain.tell(new GreeterMain.SayHello("Carlos"));
    //#main-send-messages
    greeterMain.terminate();
  }
}

