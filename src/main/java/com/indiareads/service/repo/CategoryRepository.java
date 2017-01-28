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
package com.indiareads.service.repo;

import org.springframework.data.repository.CrudRepository;

import com.indiareads.service.domain.CategoryEntity;
import com.indiareads.service.domain.UserEntity;
import com.indiareads.service.resources.CategoryResource;

/*
 *  @version     1.0, 27-Jan-2017
 *  @author gaurav
*/

public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
    
    

}
