package com.algorithm.homework.weekfour.p355;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qiuch
 * hashmap存储数据
 * 返回时调用系统排序函数
 */
public class Twitter {
    /**
     * 用户发布的推
     */
    private HashMap<Integer, List<UserTweet>> userPostedTweet;

    /**
     * 用户的关注关系，set处理重复关注
     */
    private HashMap<Integer, Set<Integer>> userRelation;

    public Twitter() {
        userPostedTweet = new HashMap<>();
        userRelation = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if (!userPostedTweet.containsKey(userId)) {
            userPostedTweet.put(userId, new ArrayList<>());
        }
        //这里尝试使用 System.currentTimeMillis()竟然相同了？！
        userPostedTweet.get(userId).add(new UserTweet(tweetId, System.nanoTime()));
    }

    public List<Integer> getNewsFeed(int userId) {
        List<UserTweet> feedInfo = new ArrayList<>();
        if (userRelation.get(userId) != null) {
            for (Integer followedUserId : userRelation.get(userId)) {
                feedInfo.addAll(userPostedTweet.getOrDefault(followedUserId, Collections.EMPTY_LIST));
            }
        }
        //加上自己发布的内容
        feedInfo.addAll(userPostedTweet.getOrDefault(userId, Collections.EMPTY_LIST));
        System.out.println(feedInfo);
        //按时间倒序排序
        feedInfo.sort(Comparator.comparing(UserTweet::getTimeStamp).reversed());
        System.out.println(feedInfo);
        if (feedInfo.size() > 10) {
            feedInfo = feedInfo.subList(0, 10);
        }
        return feedInfo.stream().map(UserTweet::getId).collect(Collectors.toList());
    }

    public void follow(int followerId, int followeeId) {
        if (!userRelation.containsKey(followerId)) {
            userRelation.put(followerId, new HashSet<>());
        }
        userRelation.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (userRelation.containsKey(followerId)) {
            userRelation.get(followerId).remove(followeeId);
        }
    }


    class UserTweet {
        int id;
        long timeStamp;

        public int getId() {
            return id;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public UserTweet(int id, long timeStamp) {
            this.id = id;
            this.timeStamp = timeStamp;
        }

        @Override
        public String toString() {
            return "UserTweet{" +
                    "id=" + id +
                    ", timeStamp=" + timeStamp +
                    '}';
        }
    }
}