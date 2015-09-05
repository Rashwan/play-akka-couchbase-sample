package controllers;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.JsonNode;
import com.rashwan.akka.messages.UserMessage;
import play.libs.F.Promise;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import static akka.pattern.Patterns.ask;

public class Application extends Controller {

    static ActorSystem system = ActorSystem.create("PlaySystem");



    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Promise<Result> addUser(){
        ActorSelection selection = system.actorSelection("akka.tcp://Kernel@127.0.0.1:2552/user/DBActor");
        JsonNode result = request().body().asJson();
        UserMessage message = null;
        if (result == null) {
            play.Logger.debug("Empty Response");
        }else {
            message = Json.fromJson(result,UserMessage.class);
        }
        return Promise.wrap(
                ask(selection,message,10000)).map(
                    o -> {
                        if (o instanceof String) {
                            String sayHi = (String) o;
                            return ok(sayHi);
                        }
                        return notFound("Didn't Say Hi :(");
                    }
        );
    }


}
