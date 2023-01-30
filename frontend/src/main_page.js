import "./index.css";
import React, { Component } from "react";
import { Stage, Layer, Line, Ellipse, Rect } from 'react-konva';

import get_host from "./shared";

import { SaveButton, LoadButton, DBSave, DBLoad, ClearButton } from "./buttons"

var address = get_host();

var side = Math.min(window.innerHeight, window.innerWidth) * 0.7;

var scale = side / 20;
var shift = side / 2;

function transform(point) {
    return {
        "x": point.x * scale + shift,
        "y": point.y * -scale + shift
    }
}

class DrawCircle extends Component {

    render() {
        return (
            <Ellipse
                x={this.props.center.x}
                y={this.props.center.y}
                radiusX={this.props.radius}
                radiusY={this.props.radius}
                stroke="black"
                key={this.props._key}
            />
        )
    }
}

class DrawOpen extends Component {
    render() {
        return (
            <Line stroke="black"
                points={
                    this.props.points.flatMap(item => [item.x, item.y])
                }
                closed={false}
                key={this.props._key} />
        )
    }
}

class DrawClosed extends Component {
    render() {
        return (
            <Line stroke="black"
                points={
                    this.props.points.flatMap(item => [item.x, item.y])
                }
                closed={false}
                key={this.props._key} />
        )
    }
}

function drawShape(shapeData) {
    if (shapeData.type === "circle") {
        return (<DrawCircle
            center={transform(shapeData.center)}
            radius={shapeData.radius / 10 * side / 2}
            key={shapeData.name}
        />)
    } else if (shapeData.type === "open") {
        return (<DrawOpen points={shapeData.points.map(transform)} _key={shapeData.name} key={shapeData.name} />)
    } else {
        return (<DrawClosed points={shapeData.points.map(transform)} _key={shapeData.name} key={shapeData.name} />)
    }
}

// function from https://stackoverflow.com/a/15832662/512042
function downloadURI(uri, name) {
    let link = document.createElement('a');
    link.download = name;
    link.href = uri;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}

const DrawApp = (props) => {
    const stageref = React.useRef(null);

    class Drawbox extends Component {
        render() {

            let a = transform({ "x": -10, "y": 0 });
            let b = transform({ "x": 10, "y": 0 });
            let c = transform({ "x": 0, "y": -10 });
            let d = transform({ "x": 0, "y": 10 });

            return (
                <div className="drawbox">
                    <Stage width={side} height={side} ref={stageref}>

                        <Layer>
                            <Rect
                                fill="#fafafa"
                                width={side}
                                height={side}
                            />
                        </Layer>

                        <Layer>
                            <Line points={
                                [a.x, a.y, b.x, b.y]
                            }
                                stroke="black"
                                strokeWidth={0.7}
                                key="graph-h-line"
                            />
                            <Line points={
                                [c.x, c.y, d.x, d.y]
                            }
                                stroke="black"
                                strokeWidth={0.7}
                                key="graph-v-line" />
                        </Layer>

                        <Layer>
                            {this.props.shapes.map(drawShape)}
                        </Layer>

                    </Stage>
                </div>
            );
        }
    }

    class UpdatingCanvas extends Component {

        constructor(props) {
            super(props);
            this.state = {
                isLoaded: false,
                shapes: [],
            };
        }

        run_update() {
            fetch(`${address}/api/shapes`)
                .then(res => res.json())
                .then(
                    (result) => {
                        this.setState({
                            isLoaded: true,
                            shapes: result
                        });
                    },
                    // Note: it's important to handle errors here
                    // instead of a catch() block so that we don't swallow
                    // exceptions from actual bugs in components.
                    (error) => {
                        this.setState({
                            isLoaded: true,
                            shapes: []
                        });
                    }
                ).catch(e => console.log(e));
        }

        componentDidMount() {
            this.run_update();

            this.interval = setInterval(() => {
                this.run_update();

                this.setState({
                    time: Date.now(),
                    isLoaded: this.state.isLoaded,
                    shapes: this.state.shapes
                });
            }, 500);
        }
        componentWillUnmount() {
            clearInterval(this.interval);
            this.setState({ cancelled: true });
        }

        render() {

            if (this.state.isLoaded) {
                return (
                    <div>
                        <Drawbox shapes={this.state.shapes} />
                    </div>

                )
            } else {
                <div>
                    <Drawbox shapes={[]} />
                </div>
            }


        }
    }

    class ImgSave extends Component {
        render() {
            return (
                <button

                    onClick={
                        () => {
                            let uri = stageref.current.toDataURL();
                            downloadURI(uri, "shapes.png");
                        }
                    }
                    className="control-button">Save image</button>
            )
        }
    }


    class Controls extends Component {
        render() {
            return (<div className="controls">
                <SaveButton />
                <LoadButton on_sucess={this.props.update} />
                <ClearButton on_sucess={this.props.update} />
                <DBSave />
                <DBLoad on_sucess={this.props.update} />
                <ImgSave />
            </div>);
        }
    }

    class DrawApp extends Component {

        constructor(props) {
            super(props);
            this.canvas = React.createRef();
        }

        handleUpdate = () => {
            this.canvas.current.run_update();
        }

        render() {

            let canvas = (<UpdatingCanvas ref={this.canvas} />);

            return (<div style={{
                "display": "flex",
                "flexDirection": "row"
            }}>
                {canvas}
                <div style={{ "paddingLeft": "30px", "paddingTop": "100px" }}>
                    <Controls update={this.handleUpdate} />
                </div>


            </div>)
        }
    }
    return <DrawApp />;
}

export default DrawApp;

export class MainPage extends Component {
    render() {
        return (
            <div>
                <DrawApp />
            </div>
        )
    }
}