import { Component } from "react";
import { ShapeSelector } from "./shape_selector";
import get_host from "./shared";

var address = get_host();

class SubmitButton extends Component {
    render() {
        return <button
            className="control-button"
            onClick={() => {
                let s1 = document.getElementById("first-selector").value;
                let s2 = document.getElementById("second-selector").value;
                console.log(s2, s2);
                if (s1 === 0 || s1) {
                    fetch(`${address}/api/shapes/${s1}/intersection/${s2}`).then(
                        data => {
                            if (data.ok) {
                                return data.json()
                            } else {
                                throw Error(data.json())
                            }
                        }
                    ).then(status => {
                        if (status) {
                            alert("фигуры пересекаются")
                        } else {
                            alert("фигуры не пересекаются")
                        }
                    }).catch(_error => {
                        alert("ошибка: фигуры несовместимы")
                    })

                }

            }}>проверить пересечение</button>
    }
}


export class IntersectionPage extends Component {
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
            <ShapeSelector options={this.state.shapes} _id={"first-selector"} />
            <ShapeSelector options={this.state.shapes} _id={"second-selector"} />
            <SubmitButton />
        </div>)
    }
}