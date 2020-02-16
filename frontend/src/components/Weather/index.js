import React, {Component} from 'react';
import {
    Button,
    Container,
    Form,
    Col,
    Row,
} from 'react-bootstrap';

import CityInfo from '../CityInfo';

class Weather extends Component {
    
    getDataFromBack = (cityName) => {
        
        if (cityName.length > 0) {
            //TODO нужно составить запрос согласно ответу сервера
            fetch(`http://localhost:8080/api/v1/weather/${cityName}`, {
                method: 'GET',
                credentials: 'same-origin',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                }
                })
            .then(response => response.json())
            .then(data => console.log(data))
            .catch(err => console.error(err));
        }
    }

    render() {
        return (
            <Container>
                <Form>
                    <Row>
                        <Col md="10">
                            <input type="text"
                                   className="form-control"
                                   placeholder="Enter City Name for getting info about weather in it"
                                   ref={input => this.cityName = input}></input>
                        </Col>
                        <Col md="2">
                            <Button onClick={() => this.getDataFromBack(this.cityName.value)}>Show info</Button>
                        </Col>
                    </Row>
                </Form>
                <CityInfo  
                id="5"
                timestamp="1234"
                temperature = "243"
                windSpeed = "10"
                message = "Test message"
                shortMessage = "message"
                weatherId = "23"
                country = "RU"
                sunset = "9"
                sunrise = "10"
                updatedAt = "12.1"
                update = "true"
                name  = "Moscow"
    />
            </Container>
            );
        }
}

export default Weather;