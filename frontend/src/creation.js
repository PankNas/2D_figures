import "./index.css";
import React, { Component } from "react";
import get_host from "./shared";

var host = get_host();

const shape_mapping = {
    "QGon": {
        "@class": "panknas.model.figures2d.QGon",
        "points": 4
    },
    "Circle": { //unusual
        "@class": "panknas.model.figures2d.Circle",
        "center": "point",
        "radius": "number"
    },
    "Tgon": {
        "@class": "panknas.model.figures2d.TGon",
        "points": 3
    },
    "Polyline": {
        "@class": "panknas.model.figures2d.Polyline",
        "points-min": 2
    },
    "Rectangle": {
        "@class": "panknas.model.figures2d.Rectangle",
        "points": 4
    },
    "NGon": {
        "@class": "panknas.model.figures2d.NGon",
        "points-min": 3
    },
    "Trapeze": {
        "@class": "panknas.model.figures2d.Trapeze",
        "points": 4,
    },
    "Segment": { //unusual
        "@class": "panknas.model.figures2d.Segment",
        "start": "point",
        "finish": "point"
    }
};

var data = {};

var varsize = 3;

var selecting = "QGon";

class TypeSelector extends Component {
    render() {
        return (<select onChange={() => { (this.props._on_update || ((_) => { }))(document.getElementById("type-selector").value) }} id="type-selector">
            {
                Object.keys(shape_mapping).map(key => {
                    return (<option key={`${key}-option`}>{key}</option>);
                })
            }
        </select >)
    }
}

function updateFactory(fieldName) {
    return () => {
        let element = document.getElementById(fieldName).value;
        data[fieldName] = element;
        console.log(element);
    }
}

function pointInputFactory(idx) {
    return (<form key={`point${idx}-form`}>
        <label key={`point${idx}-xlabel`}>{`x${idx}`}</label>
        {_numberFactory(`x${idx}`)}
        <label key={`point${idx}-ylabel`}>{`y${idx}`}</label>
        {_numberFactory(`y${idx}`)}
    </form >);
}

function numberInputFactory(name) {
    return (<form key={`${name}-form`}>
        <label>{name}</label>
        <input type="text" key={name} id={name} onInput={updateFactory(name)} />
    </form>)
}

function _numberFactory(name) {
    return (<input type="text" key={name} id={name} onInput={updateFactory(name)} />);
}

class InputComponent extends Component {

    update() {
        this.setState({ date: Date.now() });
    }

    render() {
        data = {}
        if (selecting === "Circle") {
            return (<div>
                {pointInputFactory(0)}
                {numberInputFactory("radius")}
            </div>);

        } else if (selecting === "Segment") {
            return (<div>
                {pointInputFactory(0)}
                {pointInputFactory(1)}
            </div>)

        } else if (shape_mapping[selecting]["points-min"] !== undefined) {
            let min_points = shape_mapping[selecting]["points-min"];
            varsize = Math.max(varsize, min_points);

            return (<div>
                <input type="number" value={varsize} min={min_points} max={100} onInput={() => { varsize = document.getElementById("varsize_input").value; this.update() }} id="varsize_input" />
                {[...Array(varsize).keys()].map(i => pointInputFactory(i))}
            </div>)
        } else {
            let points = shape_mapping[selecting]["points"];

            return (<div>
                {[...Array(points).keys()].map(i => pointInputFactory(i))}
            </div>)

        }
    }
}

class SubmitButton extends Component {

    check_point_range(count) {
        for (let i = 0; i < count; i++) {
            if (data["x" + i] === undefined) {
                alert(`не задан ${"x" + i}`);
                return false;
            }
            if (data["y" + i] === undefined) {
                alert(`не задан ${"y" + i}`);
                return false;
            }
        }
        return true;
    }

    check_value(name) {
        if (data[name] === undefined) {
            alert("не задан " + name);
            return false;
        }
        return true;
    }

    build_coordinates(count) {
        return [...Array(count).keys()].map(i => {
            return { "coordinates": [data["x" + i], data["y" + i]] }
        });
    }

    submit_data() {
        console.log(data);

        let payload;

        if (selecting === "Circle") {
            if (!this.check_point_range(1) || !this.check_value("radius")) {
                return;
            }

            payload = {
                "@class": shape_mapping[selecting]["@class"],
                "center": this.build_coordinates(1)[0],
                "radius": data["radius"]
            }
        } else if (selecting === "Segment") {
            if (!this.check_point_range(2)) {
                return;
            }

            let segment_points = this.build_coordinates(2);

            payload = {
                "@class": shape_mapping[selecting]["@class"],
                "start": segment_points[0],
                "finish": segment_points[1]
            }

        } else if (shape_mapping[selecting]["points-min"] !== undefined) {
            let points = varsize;
            if (!this.check_point_range(points)) {
                return;
            }
            payload = {
                "@class": shape_mapping[selecting]["@class"],
                "points": this.build_coordinates(points),
            }

        } else {
            let points = shape_mapping[selecting]["points"];
            if (!this.check_point_range(points)) {
                return;
            }
            payload = {
                "@class": shape_mapping[selecting]["@class"],
                "points": this.build_coordinates(points),
            }
        }

        fetch(`${host}/api/shapes`, {
            "method": "POST",
            "body": JSON.stringify(payload),
            headers: {
                'Content-Type': 'application/json'
            },
        }).then(resp => {
            if (!resp.ok) {
                throw Error(resp)
            }
        })

            .catch(_ => { alert("произошла ошибка") })

    }

    render() {
        return (<button onClick={() => { this.submit_data() }}>submit</button>)
    }
}

export function CreationPage(props) {

    const input_ref = React.useRef();

    class CreationPage extends Component {
        render() {

            let input = (<InputComponent ref={input_ref} />);

            let selector = (<TypeSelector _on_update={(selection) => { console.log(selection); selecting = selection; input_ref.current.update(); }} />);

            return (<div>
                {selector}
                {input}
                <SubmitButton />
                Create page
            </div>)
        }
    }
    return <CreationPage />;
}

