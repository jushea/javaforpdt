package ru.stqa.pdt.rest;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.restassured.RestAssured;
import org.testng.SkipException;

public class TestBase {
    public Boolean isIssueOpen(int issueId) {
        RestAssured.authentication = RestAssured.basic("28accbe43ea112d9feb328d2c00b3eed","");
        String json = RestAssured.get("http://demo.bugify.com/api/issues/" + issueId + ".json").asString();
        JsonElement parsed = new JsonParser().parse(json);
        String issueStatus = parsed.getAsJsonObject().get("issues")
                .getAsJsonArray().get(0)
                .getAsJsonObject().getAsJsonPrimitive("state_name").getAsString();
        if(issueStatus.equals("Open")||issueStatus.equals("In Progress")||issueStatus.equals("Re-opened")) {
            return true;
        } else {
            return false;
        }
    }

    public void skipIfNotFixed(int issueId)  {
        if(isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }
}
