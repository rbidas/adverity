import React from 'react';
import './App.css';
import Filter from "./Filter";
import ChartBox from "./ChartBox";
import EventEmitter from 'events'

export default class App extends React.Component {
    constructor(props) {
        super(props);
        this._emitter = new EventEmitter();
    }

    componentWillUnmount() {
        this._emitter.removeAllListeners();
    }

    render() {
        return (
            <div className="component-app">
                <div className="component-button-panel">
                    <div>
                        <Filter name="filer" emitter={this._emitter}/>
                        <ChartBox name="chart" emitter={this._emitter}/>
                    </div>
                </div>
            </div>
        );
    }
};
