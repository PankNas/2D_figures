import React, { Component } from "react";
import { ShapeSelector } from "./shape_selector";
import get_host from "./shared";

var address = get_host();

export function DeletePage(props) {

    class SubmitButton extends Component {
        render() {
            return <button
                className="control-button"
                onClick={() => {
                    let selection = document.getElementById("shapeSelector").value;
                    console.log(selection);

                    if (selection === 0 || selection) {
                        fetch(`${address}/api/shapes/${selection}`, { "method": "DELETE" }).then(data => {
                            if (data.ok) {
                                window.location.reload();
                            } else {
                                throw Error("error when deleting");
                            }
                        }
                        );
                    }

                }}>delete</button>
        }
    }


    class DeletePage extends Component {

        constructor(props) {
            super(props);
            this.state = {
                shapes: []
            }
        }

        request_update() {
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

        componentDidMount() {
            this.request_update();
        }

        render() {
            return (<div>
                <ShapeSelector options={this.state.shapes} />
                <SubmitButton />
            </div>)
        }
    }

    return <DeletePage />
}




