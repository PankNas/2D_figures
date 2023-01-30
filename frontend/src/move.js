import { Component } from "react";
import get_host from "./shared";
import { ShapeSelector } from "./shape_selector";

var address = get_host();
var action = { "dx": 0, "dy": 0 };

function updateFactory(fieldName) {
    return () => {
        let element = document.getElementById(fieldName).value;
        action[fieldName] = element;
        console.log(action);
    }
}

class ActionSelector extends Component {
    render() {
        let buttons;
        if (action.dx !== undefined) {
            buttons = (<form>
                <label>dx</label>
                <input type="text" id="dx" onInput={updateFactory("dx")} />
                <label>dy</label>
                <input type="text" id="dy" onInput={updateFactory("dy")} />
            </form>);
        } else if (action.angle !== undefined) {
            buttons = <form>
                <label>angle</label>
                <input type="text" id="angle" onInput={updateFactory("angle")} />
            </form>
        } else {
            buttons = <form>
                <label>axis</label>
                <input type="text" id="axis" onInput={updateFactory("axis")} />
            </form>
        }
        return (<div>
            <select onChange={() => {
                console.log("update");
                let selected = document.getElementById("actionSelector").value;
                console.log(selected);

                if (selected === "move") {
                    action = { "dx": 0, "dy": 0 };
                } else if (selected === "rotate") {
                    action = { "angle": 0 };
                } else {
                    action = { "axis": 0 };
                }
                this.setState({ time: Date.now() });

            }} id="actionSelector">
                <option>move</option>
                <option>rotate</option>
                <option>mirror</option>
            </select>
            {buttons}


        </div>)
    }
}

class SubmitButton extends Component {
    render() {
        return <button
            className="control-button"
            onClick={() => {
                let selection = document.getElementById("selector").value;
                console.log(selection);

                if (selection === 0 || selection) {
                    fetch(`${address}/api/shapes/${selection}/move`, {
                        "method": "POST",
                        body: JSON.stringify(action),
                        headers: {
                            'Content-Type': 'application/json'
                        },
                    }).then(data => {
                        if (data.ok) {
                            window.location.reload();
                        } else {
                            throw Error("ошибка в заданных значениях");
                        }
                    }
                    ).catch(err => {
                        console.log(err);
                        alert(err);
                    });
                }

            }}>move</button>
    }
}


export class MovePage extends Component {
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
            <ShapeSelector options={this.state.shapes} _id={"selector"} />
            <ActionSelector />
            <SubmitButton />
        </div>)
    }
}