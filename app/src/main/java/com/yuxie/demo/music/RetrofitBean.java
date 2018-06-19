package com.yuxie.demo.music;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */

public class RetrofitBean {

    private int total_count;

    private boolean incomplete_results;

    private List<Items> items;

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getTotal_count() {
        return this.total_count;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public boolean getIncomplete_results() {
        return this.incomplete_results;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public List<Items> getItems() {
        return this.items;
    }

    public static class Owner {
        private String login;

        private int id;

        private String avatar_url;

        private String gravatar_id;

        private String url;

        private String html_url;

        private String followers_url;

        private String following_url;

        private String gists_url;

        private String starred_url;

        private String subscriptions_url;

        private String organizations_url;

        private String repos_url;

        private String events_url;

        private String received_events_url;

        private String type;

        private boolean site_admin;

        public void setLogin(String login) {
            this.login = login;
        }

        public String getLogin() {
            return this.login;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getAvatar_url() {
            return this.avatar_url;
        }

        public void setGravatar_id(String gravatar_id) {
            this.gravatar_id = gravatar_id;
        }

        public String getGravatar_id() {
            return this.gravatar_id;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return this.url;
        }

        public void setHtml_url(String html_url) {
            this.html_url = html_url;
        }

        public String getHtml_url() {
            return this.html_url;
        }

        public void setFollowers_url(String followers_url) {
            this.followers_url = followers_url;
        }

        public String getFollowers_url() {
            return this.followers_url;
        }

        public void setFollowing_url(String following_url) {
            this.following_url = following_url;
        }

        public String getFollowing_url() {
            return this.following_url;
        }

        public void setGists_url(String gists_url) {
            this.gists_url = gists_url;
        }

        public String getGists_url() {
            return this.gists_url;
        }

        public void setStarred_url(String starred_url) {
            this.starred_url = starred_url;
        }

        public String getStarred_url() {
            return this.starred_url;
        }

        public void setSubscriptions_url(String subscriptions_url) {
            this.subscriptions_url = subscriptions_url;
        }

        public String getSubscriptions_url() {
            return this.subscriptions_url;
        }

        public void setOrganizations_url(String organizations_url) {
            this.organizations_url = organizations_url;
        }

        public String getOrganizations_url() {
            return this.organizations_url;
        }

        public void setRepos_url(String repos_url) {
            this.repos_url = repos_url;
        }

        public String getRepos_url() {
            return this.repos_url;
        }

        public void setEvents_url(String events_url) {
            this.events_url = events_url;
        }

        public String getEvents_url() {
            return this.events_url;
        }

        public void setReceived_events_url(String received_events_url) {
            this.received_events_url = received_events_url;
        }

        public String getReceived_events_url() {
            return this.received_events_url;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }

        public void setSite_admin(boolean site_admin) {
            this.site_admin = site_admin;
        }

        public boolean getSite_admin() {
            return this.site_admin;
        }
    }

    public static class Items {
        private int id;

        private String name;

        private String full_name;

        private Owner owner;

        private String html_url;

        private String description;

        private boolean fork;

        private String url;

        private String forks_url;

        private String keys_url;

        private String collaborators_url;

        private String teams_url;

        private String hooks_url;

        private String issue_events_url;

        private String events_url;

        private String assignees_url;

        private String branches_url;

        private String tags_url;

        private String blobs_url;

        private String git_tags_url;

        private String git_refs_url;

        private String trees_url;

        private String statuses_url;

        private String languages_url;

        private String stargazers_url;

        private String contributors_url;

        private String subscribers_url;

        private String subscription_url;

        private String commits_url;

        private String git_commits_url;

        private String comments_url;

        private String issue_comment_url;

        private String contents_url;

        private String compare_url;

        private String merges_url;

        private String archive_url;

        private String downloads_url;

        private String issues_url;

        private String pulls_url;

        private String milestones_url;

        private String notifications_url;

        private String labels_url;

        private String releases_url;

        private String deployments_url;

        private String created_at;

        private String updated_at;

        private String pushed_at;

        private String git_url;

        private String ssh_url;

        private String clone_url;

        private String svn_url;

        private String homepage;

        private int size;

        private int stargazers_count;

        private int watchers_count;

        private String language;

        private boolean has_issues;

        private boolean has_projects;

        private boolean has_downloads;

        private boolean has_wiki;

        private boolean has_pages;

        private int forks_count;

        private String mirror_url;

        private int open_issues_count;

        private int forks;

        private int open_issues;

        private int watchers;

        private String default_branch;

        private double score;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getFull_name() {
            return this.full_name;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }

        public Owner getOwner() {
            return this.owner;
        }

        public void setHtml_url(String html_url) {
            this.html_url = html_url;
        }

        public String getHtml_url() {
            return this.html_url;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }

        public void setFork(boolean fork) {
            this.fork = fork;
        }

        public boolean getFork() {
            return this.fork;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return this.url;
        }

        public void setForks_url(String forks_url) {
            this.forks_url = forks_url;
        }

        public String getForks_url() {
            return this.forks_url;
        }

        public void setKeys_url(String keys_url) {
            this.keys_url = keys_url;
        }

        public String getKeys_url() {
            return this.keys_url;
        }

        public void setCollaborators_url(String collaborators_url) {
            this.collaborators_url = collaborators_url;
        }

        public String getCollaborators_url() {
            return this.collaborators_url;
        }

        public void setTeams_url(String teams_url) {
            this.teams_url = teams_url;
        }

        public String getTeams_url() {
            return this.teams_url;
        }

        public void setHooks_url(String hooks_url) {
            this.hooks_url = hooks_url;
        }

        public String getHooks_url() {
            return this.hooks_url;
        }

        public void setIssue_events_url(String issue_events_url) {
            this.issue_events_url = issue_events_url;
        }

        public String getIssue_events_url() {
            return this.issue_events_url;
        }

        public void setEvents_url(String events_url) {
            this.events_url = events_url;
        }

        public String getEvents_url() {
            return this.events_url;
        }

        public void setAssignees_url(String assignees_url) {
            this.assignees_url = assignees_url;
        }

        public String getAssignees_url() {
            return this.assignees_url;
        }

        public void setBranches_url(String branches_url) {
            this.branches_url = branches_url;
        }

        public String getBranches_url() {
            return this.branches_url;
        }

        public void setTags_url(String tags_url) {
            this.tags_url = tags_url;
        }

        public String getTags_url() {
            return this.tags_url;
        }

        public void setBlobs_url(String blobs_url) {
            this.blobs_url = blobs_url;
        }

        public String getBlobs_url() {
            return this.blobs_url;
        }

        public void setGit_tags_url(String git_tags_url) {
            this.git_tags_url = git_tags_url;
        }

        public String getGit_tags_url() {
            return this.git_tags_url;
        }

        public void setGit_refs_url(String git_refs_url) {
            this.git_refs_url = git_refs_url;
        }

        public String getGit_refs_url() {
            return this.git_refs_url;
        }

        public void setTrees_url(String trees_url) {
            this.trees_url = trees_url;
        }

        public String getTrees_url() {
            return this.trees_url;
        }

        public void setStatuses_url(String statuses_url) {
            this.statuses_url = statuses_url;
        }

        public String getStatuses_url() {
            return this.statuses_url;
        }

        public void setLanguages_url(String languages_url) {
            this.languages_url = languages_url;
        }

        public String getLanguages_url() {
            return this.languages_url;
        }

        public void setStargazers_url(String stargazers_url) {
            this.stargazers_url = stargazers_url;
        }

        public String getStargazers_url() {
            return this.stargazers_url;
        }

        public void setContributors_url(String contributors_url) {
            this.contributors_url = contributors_url;
        }

        public String getContributors_url() {
            return this.contributors_url;
        }

        public void setSubscribers_url(String subscribers_url) {
            this.subscribers_url = subscribers_url;
        }

        public String getSubscribers_url() {
            return this.subscribers_url;
        }

        public void setSubscription_url(String subscription_url) {
            this.subscription_url = subscription_url;
        }

        public String getSubscription_url() {
            return this.subscription_url;
        }

        public void setCommits_url(String commits_url) {
            this.commits_url = commits_url;
        }

        public String getCommits_url() {
            return this.commits_url;
        }

        public void setGit_commits_url(String git_commits_url) {
            this.git_commits_url = git_commits_url;
        }

        public String getGit_commits_url() {
            return this.git_commits_url;
        }

        public void setComments_url(String comments_url) {
            this.comments_url = comments_url;
        }

        public String getComments_url() {
            return this.comments_url;
        }

        public void setIssue_comment_url(String issue_comment_url) {
            this.issue_comment_url = issue_comment_url;
        }

        public String getIssue_comment_url() {
            return this.issue_comment_url;
        }

        public void setContents_url(String contents_url) {
            this.contents_url = contents_url;
        }

        public String getContents_url() {
            return this.contents_url;
        }

        public void setCompare_url(String compare_url) {
            this.compare_url = compare_url;
        }

        public String getCompare_url() {
            return this.compare_url;
        }

        public void setMerges_url(String merges_url) {
            this.merges_url = merges_url;
        }

        public String getMerges_url() {
            return this.merges_url;
        }

        public void setArchive_url(String archive_url) {
            this.archive_url = archive_url;
        }

        public String getArchive_url() {
            return this.archive_url;
        }

        public void setDownloads_url(String downloads_url) {
            this.downloads_url = downloads_url;
        }

        public String getDownloads_url() {
            return this.downloads_url;
        }

        public void setIssues_url(String issues_url) {
            this.issues_url = issues_url;
        }

        public String getIssues_url() {
            return this.issues_url;
        }

        public void setPulls_url(String pulls_url) {
            this.pulls_url = pulls_url;
        }

        public String getPulls_url() {
            return this.pulls_url;
        }

        public void setMilestones_url(String milestones_url) {
            this.milestones_url = milestones_url;
        }

        public String getMilestones_url() {
            return this.milestones_url;
        }

        public void setNotifications_url(String notifications_url) {
            this.notifications_url = notifications_url;
        }

        public String getNotifications_url() {
            return this.notifications_url;
        }

        public void setLabels_url(String labels_url) {
            this.labels_url = labels_url;
        }

        public String getLabels_url() {
            return this.labels_url;
        }

        public void setReleases_url(String releases_url) {
            this.releases_url = releases_url;
        }

        public String getReleases_url() {
            return this.releases_url;
        }

        public void setDeployments_url(String deployments_url) {
            this.deployments_url = deployments_url;
        }

        public String getDeployments_url() {
            return this.deployments_url;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getCreated_at() {
            return this.created_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getUpdated_at() {
            return this.updated_at;
        }

        public void setPushed_at(String pushed_at) {
            this.pushed_at = pushed_at;
        }

        public String getPushed_at() {
            return this.pushed_at;
        }

        public void setGit_url(String git_url) {
            this.git_url = git_url;
        }

        public String getGit_url() {
            return this.git_url;
        }

        public void setSsh_url(String ssh_url) {
            this.ssh_url = ssh_url;
        }

        public String getSsh_url() {
            return this.ssh_url;
        }

        public void setClone_url(String clone_url) {
            this.clone_url = clone_url;
        }

        public String getClone_url() {
            return this.clone_url;
        }

        public void setSvn_url(String svn_url) {
            this.svn_url = svn_url;
        }

        public String getSvn_url() {
            return this.svn_url;
        }

        public void setHomepage(String homepage) {
            this.homepage = homepage;
        }

        public String getHomepage() {
            return this.homepage;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getSize() {
            return this.size;
        }

        public void setStargazers_count(int stargazers_count) {
            this.stargazers_count = stargazers_count;
        }

        public int getStargazers_count() {
            return this.stargazers_count;
        }

        public void setWatchers_count(int watchers_count) {
            this.watchers_count = watchers_count;
        }

        public int getWatchers_count() {
            return this.watchers_count;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getLanguage() {
            return this.language;
        }

        public void setHas_issues(boolean has_issues) {
            this.has_issues = has_issues;
        }

        public boolean getHas_issues() {
            return this.has_issues;
        }

        public void setHas_projects(boolean has_projects) {
            this.has_projects = has_projects;
        }

        public boolean getHas_projects() {
            return this.has_projects;
        }

        public void setHas_downloads(boolean has_downloads) {
            this.has_downloads = has_downloads;
        }

        public boolean getHas_downloads() {
            return this.has_downloads;
        }

        public void setHas_wiki(boolean has_wiki) {
            this.has_wiki = has_wiki;
        }

        public boolean getHas_wiki() {
            return this.has_wiki;
        }

        public void setHas_pages(boolean has_pages) {
            this.has_pages = has_pages;
        }

        public boolean getHas_pages() {
            return this.has_pages;
        }

        public void setForks_count(int forks_count) {
            this.forks_count = forks_count;
        }

        public int getForks_count() {
            return this.forks_count;
        }

        public void setMirror_url(String mirror_url) {
            this.mirror_url = mirror_url;
        }

        public String getMirror_url() {
            return this.mirror_url;
        }

        public void setOpen_issues_count(int open_issues_count) {
            this.open_issues_count = open_issues_count;
        }

        public int getOpen_issues_count() {
            return this.open_issues_count;
        }

        public void setForks(int forks) {
            this.forks = forks;
        }

        public int getForks() {
            return this.forks;
        }

        public void setOpen_issues(int open_issues) {
            this.open_issues = open_issues;
        }

        public int getOpen_issues() {
            return this.open_issues;
        }

        public void setWatchers(int watchers) {
            this.watchers = watchers;
        }

        public int getWatchers() {
            return this.watchers;
        }

        public void setDefault_branch(String default_branch) {
            this.default_branch = default_branch;
        }

        public String getDefault_branch() {
            return this.default_branch;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getScore() {
            return this.score;
        }
    }

    @Override
    public String toString() {
        return "RetrofitBean{" +
                "total_count=" + total_count +
                ", incomplete_results=" + incomplete_results +
                ", items=" + items +
                '}';
    }
}
