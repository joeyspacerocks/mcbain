/*
 * Copyright 2010 Joe Trewin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mcbain.examples.blog.model;

import org.mcbain.binding.BeanPropertyAccessor;
import org.mcbain.validation.PropertyValidator;
import org.mcbain.validation.ValidationResult;
import org.mcbain.validation.ValidatorBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {
	private String title;
	private String content;
	private Date creation;
	private String archiveDate;

    public Post() {
        this.creation = new Date();
        this.archiveDate = new SimpleDateFormat("yyyyMM").format(creation);
    }

    public Post(String title, String content, Date postDate) {
		this.title = title;
		this.content = content;
		this.creation = postDate;
		this.archiveDate = new SimpleDateFormat("yyyyMM").format(creation);
	}

	public String getTitle() {
		return title;
	}

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
		return content;
	}

    public void setContent(String content) {
        this.content = content;
    }

	public Date getCreation() {
		return creation;
	}

	public void modify(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public String getArchiveDate() {
		return this.archiveDate;
	}

	public boolean inArchive(String date) {
		return (archiveDate.equals(date));
	}

    // To be refactored into ActiveRecord base class

    public ValidationResult validate() {
        PropertyValidator validator = new ValidatorBuilder()
            .check("title").isNotEmpty()
            .check("content").isNotEmpty()
            .build();

        return validator.validate("post", new BeanPropertyAccessor(this));
    }
}
