DELIMITER // 
 DROP PROCEDURE IF EXISTS lowcost_schema.add_ticket;

 
CREATE PROCEDURE add_ticket ( IN `user_email` varchar(255), IN `price` decimal, 
							IN  `passenger_first_name` varchar(255), IN `passenger_last_name` varchar(255), 
                            IN `passport_number` varchar(255), IN `luggage_quantity` int, IN `luggage_price` decimal, 
                            IN `primary_boarding_right` tinyint, IN `flight_id` bigint, IN `ticket_price` decimal,
                            OUT `ticket_number` bigint, OUT `ticket_purchase_date` timestamp) 
        BEGIN
		START TRANSACTION;
        INSERT INTO ticket(`user_email`, `purchase_date`, `price`, `passenger_first_name`, `passenger_last_name`, 
							`passport_number`, `luggage_quantity`, `luggage_price`, `primary_boarding_right`,  `flight_id`) 
                            VALUES (user_email, CURRENT_TIMESTAMP(), ticket_price, passenger_first_name,
                            passenger_last_name,  passport_number, luggage_quantity, luggage_price,
                            primary_boarding_right,  flight_id);
		SELECT  LAST_INSERT_ID() INTO ticket_number FROM ticket LIMIT 1;
        SELECT purchase_date INTO ticket_purchase_date FROM ticket WHERE ticket_number = LAST_INSERT_ID() LIMIT 1;
        INSERT INTO transactions(`from_id`,  `ticket_number`, `date`, `amount`) VALUES(user_email,  ticket_number, ticket_purchase_date,  ticket_price);
		UPDATE airport_user SET balance_amount = balance_amount - ticket_price WHERE email= user_email;
		UPDATE flight SET available_places=available_places - 1 WHERE id=flight_id;      
        COMMIT;
		END //
        
     DELIMITER //    
		DROP PROCEDURE IF EXISTS lowcost_schema.remove_ticket;
		CREATE PROCEDURE remove_ticket (IN `deleted_ticket_number` bigint, IN `user_email` varchar(255), IN `flight_id` INT, 
								OUT is_deleted tinyint) 
        BEGIN
		DECLARE ticket_price decimal;
        START TRANSACTION;
		SELECT amount INTO ticket_price FROM transactions WHERE from_id = user_email AND ticket_number = deleted_ticket_number;
        CASE 
			WHEN (ticket_price IS NOT NULL) THEN 
				DELETE FROM ticket WHERE ticket_number=deleted_ticket_number;
				UPDATE flight SET available_places=available_places + 1 WHERE id=flight_id;
                UPDATE airport_user SET balance_amount = balance_amount + ticket_price WHERE email=user_email;
                INSERT INTO transactions(`from_id`,  `ticket_number`, `date`, `amount`) 
					VALUES(user_email,  deleted_ticket_number, NOW(),  -ticket_price); 
				SET is_deleted = true;
			ELSE 
				SET is_deleted=false;
		END CASE;
        COMMIT;
		END //