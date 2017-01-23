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
package com.learn.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/*
 *  @version     1.0, 11-Jan-2017
 *  @author gaurav
*/

 @JsonInclude(Include.NON_NULL)
public class Response<T> {
    
    private String      message;
    private T           data;
    private List<Error> error;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public List<Error> getError() {
        return error;
    }
    public void setError(List<Error> error) {
        this.error = error;
    }
   
}
