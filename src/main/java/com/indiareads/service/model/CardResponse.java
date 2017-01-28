/*
 * Copyright (c) 2016 10I COMMERCE SERVICES PRIVATE LIMITED. All rights reserved.
 * -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
 * This file is subject to the terms and conditions defined in file
 * 'LICENSE.txt', which is part of this source code distribution. 
 *
 * Unless required by applicable law or agreed to in writing, this file is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.indiareads.service.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
 *  @version     1.0, 27-Jan-2017
 *  @author gaurav
*/
@JsonIgnoreProperties(ignoreUnknown=true)

public class CardResponse extends GenericFormResponse {
    
    @JsonProperty
    private String title;
    
    @JsonProperty
    private String content;
    
    @JsonProperty
    private boolean isPublished;
    
    @JsonProperty
    private String photoUri;
    
    @JsonProperty
    private Long categoryId;
    
    @JsonProperty
    private Date createdDate;
    
    @JsonProperty
    private Date updatedDate;
    
 

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean isPublished) {
        this.isPublished = isPublished;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
    

}
