package com.example.administrator.starmusic.entity;

import java.io.Serializable;

public class Music implements Serializable{
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 音乐总时长
	 */
	private String duration;
	/**
	 * 曲风
	 */
	private String genre;
	/**
	 * id
	 */
	private String id;

	public void setMusicstype(String musicstype) {
		this.musicstype = musicstype;
	}

	public void setId(String id) {

		this.id = id;
	}

	public String getId() {
		return id;
	}

	/**
	 * 歌曲名字
	 */
	private String musicName;
	/**
	 * 音乐储存数据(js:upload)
	 */
	private String musicdata;
	/**
	 * 类型
	 */
	private String musicstype;

	/**
	 * 演唱者
	 */
	private String singer;


	/**
	 * 图片(js:file)
	 */
	private String picture;


	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getMusicstype() {
		return musicstype;
	}
	public void setMusicstye(String musicstype) {
		this.musicstype = musicstype;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getMusicdata() {
		return musicdata;
	}
	public void setMusicdata(String musicdata) {
		this.musicdata = musicdata;
	}

	public Music(String author, String singer, String musicstype, String genre,
				 String picture, String musicdata, String duration,String musicName) {
		super();
		this.author = author;
		this.singer = singer;
		this.musicstype = musicstype;
		this.genre = genre;
		this.picture = picture;
		this.musicdata = musicdata;
		this.setDuration(duration);
		this.musicName=musicName;
	}
	public Music() {
		super();
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getMusicName() {
		return musicName;
	}
	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}

}
