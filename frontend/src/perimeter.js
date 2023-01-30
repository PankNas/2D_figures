import React, { Component } from "react";
import { ShapeSelector } from "./shape_selector";
import get_host from "./shared";

var address = get_host();

class SubmitButton extends Component {
    render() {
        return <button
            className="control-button"
            onClick={() => {
                let selection = document.getElementById("shapeSelector").value;
                console.log(selection);
                if (selection === 0 || selection) {
                    fetch(`${address}/api/shapes/${selection}/perimeter`).then(
                        data => {
                            if (data.ok) {
                                return data.json()
                            } else {
                                throw Error(data.json())
                            }
                        }
                    ).then(area => alert(`perimeter: ${area}`))
                }

            }}>compute</button>
    }
}


export class PerimeterPage extends Component {

    constructor(props) {
        super(props);
        this.state = {
            shapes: []
        }
    }

    componentDidMount() {
        fetch(`${address}/api/shapes`)
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        isLoaded: true,
                        shapes: result
                    });
                },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        shapes: []
                    });
                }
            ).catch(e => console.log(e));
    }

    render() {
        return (<div>
            <ShapeSelector options={this.state.shapes} />
            <SubmitButton />
        </div>)
    }
}




