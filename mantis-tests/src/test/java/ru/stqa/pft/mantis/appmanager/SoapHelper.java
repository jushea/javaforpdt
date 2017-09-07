package ru.stqa.pft.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {

    private ApplicationManager app;

    public SoapHelper(ApplicationManager app) {
        this.app = app;
    }

    public Set<Project> getProjects() throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        ProjectData[] projects = mc.mc_projects_get_user_accessible(app.getProperty("mantis.login"), app.getProperty("mantis.password"));
        return Arrays.asList(projects).stream()
                .map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName())).collect(Collectors.toSet());
    }

    public MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
        return new MantisConnectLocator()
                .getMantisConnectPort(new URL(app.getProperty("mantis.soapurl")));
    }

    public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        String[] categories = mc.mc_project_get_categories(app.getProperty("mantis.login"), app.getProperty("mantis.password"), BigInteger.valueOf(issue.getProject().getId()));
        IssueData issueData = new IssueData();
        issueData.setSummary(issue.getSummary());
        issueData.setDescription(issue.getDescription());
        issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
        issueData.setCategory(categories[0]);
        BigInteger issueId = mc.mc_issue_add(app.getProperty("mantis.login"), app.getProperty("mantis.password"), issueData);
        IssueData createdIssueData = mc.mc_issue_get(app.getProperty("mantis.login"), app.getProperty("mantis.password"), issueId);
        return new Issue().withId(createdIssueData.getId().intValue())
                .withSummary(createdIssueData.getSummary()).withDescription(createdIssueData.getDescription())
                .withProject(new Project().withId(createdIssueData.getProject().getId().intValue())
                                          .withName(createdIssueData.getProject().getName()));
    }

    public void resolvedIssue(Issue created, int issueId) throws ServiceException, MalformedURLException, RemoteException {
        MantisConnectPortType mc = app.soap().getMantisConnect();

        IssueData issuedata = new IssueData();

        String[] categories = mc.mc_project_get_categories(app.getProperty("mantis.login"), app.getProperty("mantis.password"), BigInteger.valueOf(created.getProject().getId()));
        issuedata.setCategory(categories[0]);
        issuedata.setProject(new ObjectRef(BigInteger.valueOf(created.getProject().getId()), created.getProject().getName()));
        issuedata.setStatus(new ObjectRef(BigInteger.valueOf(80), "resolved"));
        issuedata.setResolution(new ObjectRef(BigInteger.valueOf(10), "open"));
        issuedata.setSummary(created.getSummary());
        issuedata.setDescription(created.getDescription());

        boolean b = mc.mc_issue_update(app.getProperty("mantis.login"), app.getProperty("mantis.password"), BigInteger.valueOf(issueId), issuedata);
    }
}
