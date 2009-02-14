// Copyright 2007 Joe Trewin
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.mcbain.examples.blog.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Blog {
	private String name;
	private String description;
	private Date creation;
	private List<Post> posts;
	private List<String> archiveDates;

	public Blog(String name) {
		this.name = name;
		this.creation = new Date();
		this.posts = new ArrayList<Post>();
		this.archiveDates = new ArrayList<String>();
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Date getCreation() {
		return creation;
	}

	public List<Post> latestPosts() {
		int count = Math.min(2, posts.size());
		List<Post> latest = new ArrayList<Post>(count);

		for (int i = 0; i < count; i++) {
			latest.add(posts.get(posts.size() - 1 - i));
		}

		return latest;
	}

	public List<Post> archivedPosts(String date) {
		List<Post> result = new ArrayList<Post>();

		for (Post p : posts) {
			if (p.inArchive(date)) result.add(p);
		}

		return result;
	}

	public void addPost(String title, String content) {
		addPost(new Post(this, title, content));
	}

	public void addPost(String title, String content, Date postDate) {
		addPost(new Post(this, title, content, postDate));
	}

	public void modifyPost(String originalTitle, String title, String content) {
		Post post = getPost(originalTitle);
		post.modify(title, content);
	}

	public List<String> getArchives() {
		return archiveDates;
	}

	public Post getPost(String title) {
		for (Post p : posts) {
			if (p.getTitle().equals(title)) {
				return p;
			}
		}

		return null;
	}

	private void addPost(Post post) {
		posts.add(post);

		String archive = post.getArchiveDate();

		if (!archiveDates.contains(archive))
			archiveDates.add(0, archive);
	}
}
