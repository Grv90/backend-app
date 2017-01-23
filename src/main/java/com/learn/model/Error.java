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

/*
 *  @version     1.0, 11-Jan-2017
 *  @author gaurav
*/

public class Error {
    private int    code;
    private String message;
    
    public Error() {
    }

    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
    

}
