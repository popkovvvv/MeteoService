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

    constructor(props) {
        super(props);

        this.state = {
            data: [],
            isLoad: false,
        };
    }
    
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
            .then(res => this.setState({data: res, isLoad: true}))
            .catch(err => console.error(err));
        }
    }

    render() {
        const { data, isLoad } = this.state;
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
                {!isLoad ? <h1>Введите город</h1> :
                <CityInfo  
                id={data.id}
                timestamp={data.timestamp}
                temperature = {data.temperature}
                windSpeed = {data.windSpeed}
                message = {data.message}
                shortMessage = {data.shortMessage}
                weatherId = {data.weatherId}
                country = {data.country}
                sunset = {data.sunset}
                sunrise = {data.sunrise}
                updatedAt = {data.updatedAt}
                update = {data.update}
                name  = {data.name}
                />}
            </Container>
            );
        }
}

export default Weather;