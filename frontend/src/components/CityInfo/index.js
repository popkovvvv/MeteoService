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
    faTint,
    faTemperatureHigh,
    faThermometerEmpty,
    faCloud,
    faWind
} from '@fortawesome/free-solid-svg-icons';

export default ({
    cityName,
    zipCode,
    temperature,
    feelsLike,
    tempMin,
    tempMax,
    pressure,
    humidity,
    infoStatus
}) => 
    <Card style={{ width: '40rem', margin: "40px 0 0 12%" }}>
        <Card.Body>
            <Card.Title>
                {cityName}
                <Badge style={{color: '#666', fontSize: '12px'}}>
                    (Zip Code: <b>{zipCode}</b>)
                </Badge>
            </Card.Title>
        </Card.Body>
        <ListGroup className="list-group-flush">
            <ListGroupItem>
                <FontAwesomeIcon icon={faThermometerEmpty} style={{marginRight: 10}} />
                {pressure}
            </ListGroupItem>
            <ListGroupItem>
                <FontAwesomeIcon icon={faTemperatureHigh} style={{marginRight: 10}} />
                {temperature} 
                |
                <small style={{color: 'red'}}>High: {tempMax}</small>
                |
                <small style={{color: 'blue'}}>Low: {tempMin}</small>
                <div style={{float: 'right', color: '#666'}}>
                    Feels like {feelsLike}
                </div>
            </ListGroupItem>
            <ListGroupItem>
                <FontAwesomeIcon icon={faTint} style={{marginRight: 10}} />
                {humidity}
            </ListGroupItem>
        </ListGroup>
        <Alert variant="primary">
            <h3>
                <FontAwesomeIcon icon={faWind} style={{marginRight: 10}} />
                Wind
            </h3>
            <Badge pill variant="primary">
                Speed: {infoStatus.wind.speed}
            </Badge>  
            <Badge pill variant="primary">
                Deg: {infoStatus.wind.speed}
            </Badge>         
        </Alert>
        <Alert variant="secondary">
            <h3>
                <FontAwesomeIcon icon={faCloud} style={{marginRight: 10}} />
                Clouds
            </h3>
            <Badge pill variant="secondary">
                {infoStatus.clouds.all}
            </Badge>        
        </Alert>
    </Card>;