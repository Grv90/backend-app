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
package com.indiareads.service.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.indiareads.service.domain.CardEntity;
import com.indiareads.service.domain.UserEntity;
import com.indiareads.service.model.CardResponse;
import com.indiareads.service.model.GenericFormResponse;
import com.indiareads.service.model.SuccessCode;
import com.indiareads.service.repo.CardsRepository;
import com.indiareads.service.repo.UsersRepository;

/*
 *  @version     1.0, 27-Jan-2017
 *  @author gaurav
*/

@RestController
@RequestMapping(value = "/v1/cards")
public class CardResource {
    private static final Logger log = LoggerFactory.getLogger(UsersResource.class);

    @Autowired
    private CardsRepository     cardsRepository;

    @Autowired
    private UsersRepository     userRepository;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/{userid}", method = RequestMethod.GET)
    public ResponseEntity<List<CardResponse>> getAllCards(@PathVariable("userid") String userid) {
        List<CardEntity> response = null;
        UserEntity record = null;
        List<CardResponse> cardResponse = new ArrayList<>();
        try {
            record = userRepository.findById(Long.valueOf(userid));
        } catch (Exception e) {
            String message = "Error encountered while fetching users from the DB";
            log.error(message, e);
        }

        if (record == null) {
            String message = "User with id " + userid + " not found in the system";
            log.error(message);
            return new ResponseEntity<>(cardResponse, HttpStatus.NOT_FOUND);
        }

        try {
            response = cardsRepository.findByCreatedBy(Long.valueOf(userid));
        } catch (Exception e) {
            String message = "Error encountered while fetching users from the DB";
            log.error(message, e);
        }
        cardResponse = setAttributesInCardResponse(response);

        return new ResponseEntity<>(cardResponse, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/status/{id}", method = RequestMethod.PUT)
    public ResponseEntity<GenericFormResponse> deactivateCard(@PathVariable("id") Long id) {
        GenericFormResponse response = new GenericFormResponse();
        String status=null;

        CardEntity record = null;
        try {
            record = cardsRepository.findById(id);
        } catch (Exception e) {
            log.error("Error encountered while fetching users from the DB", e);
        }
        if (record == null) {
            String message = "Card with CardId " + id + " Not found in the system";
            log.error(message);
        }
        if (record.getIsPublished() == true) {
            record.setIsPublished(false);
            status="Activated";
        } else {
            record.setIsPublished(true);
            status="Deactivated";
        }
        cardsRepository.save(record);

        String message = "Succeefully "+status+" the Card";
        response.setStatusCode(1002);
        SuccessCode success = new SuccessCode("OK", message);
        List<SuccessCode> successList = new ArrayList<>();
        successList.add(success);
        response.setSuccess(successList);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<GenericFormResponse> addNewCard(@RequestBody CardEntity card) {
        GenericFormResponse response = new GenericFormResponse();

        card.setCreatedDate(new Date());
        card.setUpdatedDate(new Date());
        try {
            cardsRepository.save(card);
        } catch (Exception e) {
            log.equals(e);
        }

        String message = "You have Successfully Added New Card.";
        response.setStatusCode(1002);
        SuccessCode success = new SuccessCode("OK", message);
        List<SuccessCode> successList = new ArrayList<>();
        successList.add(success);
        response.setSuccess(successList);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    private List<CardResponse> setAttributesInCardResponse(List<CardEntity> response) {
        // TODO Auto-generated method stub
        List<CardResponse> cardResponseList = new ArrayList<>();
        for (CardEntity entity : response) {
            CardResponse cardResponse = new CardResponse();
            cardResponse.setTitle(entity.getTitle());
            cardResponse.setPhotoUri(entity.getPhotoUri());
            cardResponse.setContent(entity.getContent());
            cardResponse.setPublished(entity.getIsPublished());
            cardResponse.setCreatedDate(entity.getCreatedDate());
            cardResponse.setUpdatedDate(entity.getUpdatedDate());
            cardResponseList.add(cardResponse);

        }

        return cardResponseList;
    }

}
