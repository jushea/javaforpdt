package ru.stqa.pdt.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {
    @Test
    public void testCommits() throws IOException {
        Github github = new RtGithub("739219be1425edcab43dd47d3b96404461456623");
        RepoCommits commits = github.repos().get(new Coordinates.Simple("jushea", "javaforpdt")).commits();
        for(RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
            System.out.println(new RepoCommit.Smart(commit).message());
        }

    }
}
