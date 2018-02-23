package com.b3ad.yuxie.myapplication.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/08/22.
 */

public class Musics {

    /**
     * result : {"songCount":1500,"songs":[{"id":457605444,"name":"你好。","artists":[{"id":0,"name":"你好张漾","picUrl":null}],"album":{"id":0,"name":"[DJ节目]你好张漾的DJ节目 第1期","artist":{"id":0,"name":"你好张漾","picUrl":null},"picUrl":"http://p1.music.126.net/DbhuuyhzyTlGaPZcrA0Ziw==/18726882046048058.jpg"},"audio":"http://m2.music.126.net/jSDg12jsAZjxiWskxiHmZQ==/18998461416354634.mp3","djProgramId":900660748,"page":"http://music.163.com/m/song/457605444"},{"id":31877060,"name":"你好，那时","artists":[{"id":1074004,"name":"曲艺","picUrl":null}],"album":{"id":3142009,"name":"你好，那时","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/S7itc7MnfvxAh0IgCHNbtA==/2941193605503506.jpg"},"audio":"http://m2.music.126.net/Xma08voEU2-M2Po0fuxrIQ==/7728467232417541.mp3","djProgramId":0,"page":"http://music.163.com/m/song/31877060"},{"id":31877054,"name":"你好，那时","artists":[{"id":1074003,"name":"种丹妮","picUrl":null}],"album":{"id":3142008,"name":"你好，那时","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/I64BM5CwgmJGi8z8zZcqow==/2901611186844767.jpg"},"audio":"http://m2.music.126.net/J-Dr6Cho_Oa0KCRq0q5pGQ==/2887317535736223.mp3","djProgramId":0,"page":"http://music.163.com/m/song/31877054"},{"id":445702159,"name":"你好疯子","artists":[{"id":7647,"name":"陈嘉桦","picUrl":null}],"album":{"id":35042040,"name":"你好疯子","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/niiaQbQ5J93PLCnV53MBMQ==/18735678138854753.jpg"},"audio":"http://m2.music.126.net/Gy7jSUyAo2w2jtnLFTPldA==/18678503534793560.mp3","djProgramId":0,"page":"http://music.163.com/m/song/445702159"},{"id":27570663,"name":"你好，旧时光","artists":[{"id":808050,"name":"杨炅翰","picUrl":null}],"album":{"id":2639286,"name":"你好，旧时光","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/XpxRlPWvyYQ5SQOjTAnQ8A==/5518448859893247.jpg"},"audio":"http://m2.music.126.net/yjdtWxZwh38Qsg77dWjQog==/5663584394748983.mp3","djProgramId":0,"page":"http://music.163.com/m/song/27570663"},{"id":29849902,"name":"你好,再见","artists":[{"id":9203,"name":"戚薇","picUrl":null}],"album":{"id":3081891,"name":"你好，再见","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/WF6B0xsnW08swQJxOFk2yQ==/3235862723343630.jpg"},"audio":"http://m2.music.126.net/_naatPaf2lgORAa-X_YT8Q==/3238061746639961.mp3","djProgramId":0,"page":"http://music.163.com/m/song/29849902"},{"id":499578590,"name":"你好☺","artists":[{"id":0,"name":"琴文君","picUrl":null}],"album":{"id":0,"name":"[DJ节目]琴文君的DJ节目 第1期","artist":{"id":0,"name":"琴文君","picUrl":null},"picUrl":"http://p1.music.126.net/OL3o7NJ63mWLW7P702ucBg==/18894007811833718.jpg"},"audio":"http://m2.music.126.net/hmZoNQaqzZALvVp0rE7faA==/0.mp3","djProgramId":908790292,"page":"http://music.163.com/m/song/499578590"},{"id":39987448,"name":"你好---","artists":[{"id":0,"name":"慢慢声","picUrl":null}],"album":{"id":0,"name":"[DJ节目]慢慢声的DJ节目 第5期","artist":{"id":0,"name":"慢慢声","picUrl":null},"picUrl":"http://p1.music.126.net/-DKltTGYLCL6FhULhZgjmQ==/5794426278485969.jpg"},"audio":"http://m2.music.126.net/Mla9tz2sXXiaC35qyO95Kw==/3223768097018090.mp3","djProgramId":15496206,"page":"http://music.163.com/m/song/39987448"},{"id":39886822,"name":"你好---","artists":[{"id":0,"name":"慢慢声","picUrl":null}],"album":{"id":0,"name":"[DJ节目]慢慢声的DJ节目 第4期","artist":{"id":0,"name":"慢慢声","picUrl":null},"picUrl":"http://p1.music.126.net/6fv7pxY_Ps0pBvVQPzMX2Q==/3276544657451671.jpg"},"audio":"http://m2.music.126.net/CpO5l_YAkJWBb0dgJZWoEA==/3223768096950236.mp3","djProgramId":15518167,"page":"http://music.163.com/m/song/39886822"}]}
     * code : 200
     */

    private ResultBean result;
    private int code;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ResultBean {
        /**
         * songCount : 1500
         * songs : [{"id":457605444,"name":"你好。","artists":[{"id":0,"name":"你好张漾","picUrl":null}],"album":{"id":0,"name":"[DJ节目]你好张漾的DJ节目 第1期","artist":{"id":0,"name":"你好张漾","picUrl":null},"picUrl":"http://p1.music.126.net/DbhuuyhzyTlGaPZcrA0Ziw==/18726882046048058.jpg"},"audio":"http://m2.music.126.net/jSDg12jsAZjxiWskxiHmZQ==/18998461416354634.mp3","djProgramId":900660748,"page":"http://music.163.com/m/song/457605444"},{"id":31877060,"name":"你好，那时","artists":[{"id":1074004,"name":"曲艺","picUrl":null}],"album":{"id":3142009,"name":"你好，那时","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/S7itc7MnfvxAh0IgCHNbtA==/2941193605503506.jpg"},"audio":"http://m2.music.126.net/Xma08voEU2-M2Po0fuxrIQ==/7728467232417541.mp3","djProgramId":0,"page":"http://music.163.com/m/song/31877060"},{"id":31877054,"name":"你好，那时","artists":[{"id":1074003,"name":"种丹妮","picUrl":null}],"album":{"id":3142008,"name":"你好，那时","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/I64BM5CwgmJGi8z8zZcqow==/2901611186844767.jpg"},"audio":"http://m2.music.126.net/J-Dr6Cho_Oa0KCRq0q5pGQ==/2887317535736223.mp3","djProgramId":0,"page":"http://music.163.com/m/song/31877054"},{"id":445702159,"name":"你好疯子","artists":[{"id":7647,"name":"陈嘉桦","picUrl":null}],"album":{"id":35042040,"name":"你好疯子","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/niiaQbQ5J93PLCnV53MBMQ==/18735678138854753.jpg"},"audio":"http://m2.music.126.net/Gy7jSUyAo2w2jtnLFTPldA==/18678503534793560.mp3","djProgramId":0,"page":"http://music.163.com/m/song/445702159"},{"id":27570663,"name":"你好，旧时光","artists":[{"id":808050,"name":"杨炅翰","picUrl":null}],"album":{"id":2639286,"name":"你好，旧时光","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/XpxRlPWvyYQ5SQOjTAnQ8A==/5518448859893247.jpg"},"audio":"http://m2.music.126.net/yjdtWxZwh38Qsg77dWjQog==/5663584394748983.mp3","djProgramId":0,"page":"http://music.163.com/m/song/27570663"},{"id":29849902,"name":"你好,再见","artists":[{"id":9203,"name":"戚薇","picUrl":null}],"album":{"id":3081891,"name":"你好，再见","artist":{"id":0,"name":"","picUrl":null},"picUrl":"http://p1.music.126.net/WF6B0xsnW08swQJxOFk2yQ==/3235862723343630.jpg"},"audio":"http://m2.music.126.net/_naatPaf2lgORAa-X_YT8Q==/3238061746639961.mp3","djProgramId":0,"page":"http://music.163.com/m/song/29849902"},{"id":499578590,"name":"你好☺","artists":[{"id":0,"name":"琴文君","picUrl":null}],"album":{"id":0,"name":"[DJ节目]琴文君的DJ节目 第1期","artist":{"id":0,"name":"琴文君","picUrl":null},"picUrl":"http://p1.music.126.net/OL3o7NJ63mWLW7P702ucBg==/18894007811833718.jpg"},"audio":"http://m2.music.126.net/hmZoNQaqzZALvVp0rE7faA==/0.mp3","djProgramId":908790292,"page":"http://music.163.com/m/song/499578590"},{"id":39987448,"name":"你好---","artists":[{"id":0,"name":"慢慢声","picUrl":null}],"album":{"id":0,"name":"[DJ节目]慢慢声的DJ节目 第5期","artist":{"id":0,"name":"慢慢声","picUrl":null},"picUrl":"http://p1.music.126.net/-DKltTGYLCL6FhULhZgjmQ==/5794426278485969.jpg"},"audio":"http://m2.music.126.net/Mla9tz2sXXiaC35qyO95Kw==/3223768097018090.mp3","djProgramId":15496206,"page":"http://music.163.com/m/song/39987448"},{"id":39886822,"name":"你好---","artists":[{"id":0,"name":"慢慢声","picUrl":null}],"album":{"id":0,"name":"[DJ节目]慢慢声的DJ节目 第4期","artist":{"id":0,"name":"慢慢声","picUrl":null},"picUrl":"http://p1.music.126.net/6fv7pxY_Ps0pBvVQPzMX2Q==/3276544657451671.jpg"},"audio":"http://m2.music.126.net/CpO5l_YAkJWBb0dgJZWoEA==/3223768096950236.mp3","djProgramId":15518167,"page":"http://music.163.com/m/song/39886822"}]
         */

        private int songCount;
        private List<SongsBean> songs;

        public int getSongCount() {
            return songCount;
        }

        public void setSongCount(int songCount) {
            this.songCount = songCount;
        }

        public List<SongsBean> getSongs() {
            return songs;
        }

        public void setSongs(List<SongsBean> songs) {
            this.songs = songs;
        }

        public static class SongsBean {
            /**
             * id : 457605444
             * name : 你好。
             * artists : [{"id":0,"name":"你好张漾","picUrl":null}]
             * album : {"id":0,"name":"[DJ节目]你好张漾的DJ节目 第1期","artist":{"id":0,"name":"你好张漾","picUrl":null},"picUrl":"http://p1.music.126.net/DbhuuyhzyTlGaPZcrA0Ziw==/18726882046048058.jpg"}
             * audio : http://m2.music.126.net/jSDg12jsAZjxiWskxiHmZQ==/18998461416354634.mp3
             * djProgramId : 900660748
             * page : http://music.163.com/m/song/457605444
             */

            private int id;
            private String name;
            private AlbumBean album;
            private String audio;
            private int djProgramId;
            private String page;
            private List<ArtistsBean> artists;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public AlbumBean getAlbum() {
                return album;
            }

            public void setAlbum(AlbumBean album) {
                this.album = album;
            }

            public String getAudio() {
                return audio;
            }

            public void setAudio(String audio) {
                this.audio = audio;
            }

            public int getDjProgramId() {
                return djProgramId;
            }

            public void setDjProgramId(int djProgramId) {
                this.djProgramId = djProgramId;
            }

            public String getPage() {
                return page;
            }

            public void setPage(String page) {
                this.page = page;
            }

            public List<ArtistsBean> getArtists() {
                return artists;
            }

            public void setArtists(List<ArtistsBean> artists) {
                this.artists = artists;
            }

            public static class AlbumBean {
                /**
                 * id : 0
                 * name : [DJ节目]你好张漾的DJ节目 第1期
                 * artist : {"id":0,"name":"你好张漾","picUrl":null}
                 * picUrl : http://p1.music.126.net/DbhuuyhzyTlGaPZcrA0Ziw==/18726882046048058.jpg
                 */

                private int id;
                private String name;
                private ArtistBean artist;
                private String picUrl;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public ArtistBean getArtist() {
                    return artist;
                }

                public void setArtist(ArtistBean artist) {
                    this.artist = artist;
                }

                public String getPicUrl() {
                    return picUrl;
                }

                public void setPicUrl(String picUrl) {
                    this.picUrl = picUrl;
                }

                public static class ArtistBean {
                    /**
                     * id : 0
                     * name : 你好张漾
                     * picUrl : null
                     */

                    private int id;
                    private String name;
                    private Object picUrl;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public Object getPicUrl() {
                        return picUrl;
                    }

                    public void setPicUrl(Object picUrl) {
                        this.picUrl = picUrl;
                    }
                }
            }

            public static class ArtistsBean {
                /**
                 * id : 0
                 * name : 你好张漾
                 * picUrl : null
                 */

                private int id;
                private String name;
                private Object picUrl;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Object getPicUrl() {
                    return picUrl;
                }

                public void setPicUrl(Object picUrl) {
                    this.picUrl = picUrl;
                }
            }
        }
    }
}
