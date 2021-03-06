package com.keychat.dto.base;

import java.io.Serializable;

public class ChannelsKickoutModel implements Serializable {
	private int channels_kickout_id;
	private String channel_name;
	private String email;

	public ChannelsKickoutModel() {
		super();
	}

	public ChannelsKickoutModel(int channels_kickout_id, String channel_name, String email) {
		super();
		this.channels_kickout_id = channels_kickout_id;
		this.channel_name = channel_name;
		this.email = email;
	}

	public int getChannels_kickout_id() {
		return channels_kickout_id;
	}

	public void setChannels_kickout_id(int channels_kickout_id) {
		this.channels_kickout_id = channels_kickout_id;
	}

	public String getChannel_name() {
		return channel_name;
	}

	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "channels_kickout_dto [channels_kickout_id=" + channels_kickout_id + ", channel_name=" + channel_name
				+ ", email=" + email + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((channel_name == null) ? 0 : channel_name.hashCode());
		result = prime * result + channels_kickout_id;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChannelsKickoutModel other = (ChannelsKickoutModel) obj;
		if (channel_name == null) {
			if (other.channel_name != null)
				return false;
		} else if (!channel_name.equals(other.channel_name))
			return false;
		if (channels_kickout_id != other.channels_kickout_id)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	
}