import React, {Component} from 'react';
import {
    Button,
    Container,
    Form,
    Col,
    Row,
    FormControl
} from 'react-bootstrap';

import CityInfo from '../CityInfo';

class Weather extends Component {
    getDataFromBack = (cityName) => {
        if (cityName.length > 0) {
            //TODO нужно составить запрос согласно ответу сервера
            fetch(`http://server/weather/${cityName}`, {
                method: 'GET',
                data: {
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
                   cityName="Moscow"
                   zipCode="123456"
                   temperature="80.3"
                   tempMax="81."
                   tempMin="76.2"
                   feelsLike="70"
                   pressure="12345"
                   humidity="93"
                   infoStatus={{
                        "wind": {
                            "speed": 0.47,
                            "deg": 107.538
                        },
                        "clouds": {
                            "all": 2
                        }
                   }} />
            </Container>
            );
        }
}

export default Weather;