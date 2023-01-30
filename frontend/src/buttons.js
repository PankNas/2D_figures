import React, { Component } from "react";
import "./index.css";

import get_host from "./shared";


export class SaveButton extends Component {
    render() {
        return (
            <button
                onClick={() => {
                    fetch(`${get_host()}/api/state`).then(content => content.json()).then(data => {
                        let a = document.createElement("a");
                        a.href = window.URL.createObjectURL(new Blob([JSON.stringify(data)]), { type: "text/plain" });
                        a.download = "shapes.json";
                        a.click();
                    })
                }}
                className="control-button"> Save </button>
        );
    }
}

export class LoadButton extends Component {
    constructor(props) {
        super(props);
        let reader = new FileReader();

        function upload_file(e) {
            let content = reader.result;

            fetch(`${get_host()}/api/state`,
                {
                    method: "POST",
                    body: content,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                })
                .then(_ => {
                    let on_sucess = props.on_success || (() => { });
                    on_sucess();
                })

                .catch(e => console.error("failed upload:", e))
        }

        reader.onloadend = upload_file;

        this.state = { reader: reader, inputOpenFileRef: React.createRef() };
    }

    showOpenFileDialog = () => {
        this.state.inputOpenFileRef.current.click()
    }

    render() {
        return <div>
            <input
                type="file"
                id="file"
                onChange={
                    (event) => {
                        let file = event.target.files[0];
                        if (!file) {
                            return;
                        }

                        this.state.reader.readAsText(file);
                        event.target.value = null;
                    }
                }
                ref={this.state.inputOpenFileRef}
                style={{ display: 'none' }}
            />
            <button className="control-button"
                onClick={
                    this.showOpenFileDialog
                }>Load</button>
        </div>
    }
}

export class ClearButton extends Component {
    render() {
        return (
            <button
                onClick={() => {
                    fetch(`${get_host()}/api/shapes`, { method: "DELETE" })
                        .then((response) => {
                            if (response.ok) {
                                return null;
                            }
                            throw response;
                        })
                        .then(_ => {
                            let on_sucess = this.props.on_success || (() => { });
                            on_sucess();
                            //alert(`success: cleared shapes`);

                        })
                        .catch(err => {
                            err.json().then((body) => {
                                console.log(body);

                                alert(`error: ${body.message}`);
                            });

                        })
                }}
                className="control-button"> Clear </button>
        );
    }
}

export class DBSave extends Component {
    render() {
        return (
            <button
                onClick={() => {
                    fetch(`${get_host()}/api/db-save`, { method: "POST" })
                        .then((response) => {
                            if (response.ok) {
                                return null;
                            }
                            throw response;
                        })
                        .then(_ => {
                            let on_sucess = this.props.on_success || (() => { });
                            on_sucess();
                            //alert(`success: saved data to db`);

                        })
                        .catch(err => {
                            err.json().then((body) => {
                                console.log(body);

                                alert(`error: ${body.message}`);
                            });

                        })
                }}
                className="control-button"> Save to DB </button>
        );
    }
}

export class DBLoad extends Component {
    render() {
        return (
            <button
                onClick={() => {
                    fetch(`${get_host()}/api/db-load`, { method: "POST" })
                        .then((response) => {
                            if (response.ok) {
                                return null;
                            }
                            throw response;
                        })
                        .then(_ => {
                            let on_sucess = this.props.on_success || (() => { });
                            on_sucess();
                            //alert(`success: loaded data from db`);

                        })
                        .catch(err => {
                            err.json().then((body) => {
                                console.log(body);

                                alert(`error: ${body.message}`);
                            });

                        })
                }}
                className="control-button"> Load from DB </button>
        );
    }
}