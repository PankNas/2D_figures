import { Component } from "react";

export class ShapeSelector extends Component {
    render() {

        let selectorId = this.props._id || "shapeSelector";

        let options = this.props.options.map((k) => {
            return (<option value={k.idx} key={k.name}>{k.name}</option>);
        });

        return (<div>
            <select name="shapeSelect" id={selectorId}>
                {options}
            </select>
        </div>)
    }
}