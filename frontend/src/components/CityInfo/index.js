import React from 'react';
import {
    Card,
    ListGroup,
    ListGroupItem,
    Badge,
    Alert
} from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faTemperatureHigh,
    faThermometerEmpty,
    faWind
} from '@fortawesome/free-solid-svg-icons';

export default ({
    id,
    timestamp,
    temperature,
    windSpeed,
    message,
    shortMessage,
    weatherId,
    country,
    sunset,
    sunrise,
    updatedAt,
    update,
    name
}) => 
    <Card style={{ width: '40rem', margin: "40px 0 0 12%" }}>
        <Card.Body>
            <Card.Title>
                {name + country}
                <Badge style={{color: '#666', fontSize: '12px'}}>
                    (ID: <b>{id}</b>)
                </Badge>
            </Card.Title>
        </Card.Body>
        <ListGroup className="list-group-flush">
            <ListGroupItem>
                <FontAwesomeIcon icon={faThermometerEmpty} style={{marginRight: 10}} />
                {windSpeed}
            </ListGroupItem>
            <ListGroupItem>
                <FontAwesomeIcon icon={faTemperatureHigh} style={{marginRight: 10}} />
                {temperature} 
            </ListGroupItem>
            <ListGroupItem>
                <p>{message}</p>
                <p>{shortMessage}</p>
            </ListGroupItem>
        </ListGroup>
        <Alert variant="primary">
            <h3>
                <FontAwesomeIcon icon={faWind} style={{marginRight: 10}} />
                Meta information
            </h3>
            <Badge pill variant="primary">
                Speed: {windSpeed}
            </Badge>  
            <Badge pill variant="primary">
                Sunset: {sunset}
            </Badge>  
            <Badge pill variant="primary">
                Sunrise: {sunrise}
            </Badge>        
        </Alert>
        <Alert variant="secondary">
            <h3>
                <FontAwesomeIcon icon={update} style={{marginRight: 10}} />
                Update: {update}
            </h3>        
        </Alert>
    </Card>;