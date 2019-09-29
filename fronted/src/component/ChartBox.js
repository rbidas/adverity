import React from "react";
import Chart from "react-apexcharts";
import './ChartBox.css'
const DATA_URL = "http://localhost:8080/data";

export default class ChartBox extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            title: "",
            options: {
                chart: {
                    height: 350,
                    id: "basic-bar",
                    type: "line"
                },
                xaxis: {
                    categories: [],
                    type: "datetime",
                    labels: {
                        datetimeFormatter: {
                            year: 'yyyy',
                            month: 'MMM \'yy',
                            day: 'dd MMM',
                            hour: 'HH:mm'
                        }
                    }
                },
                yaxis: [
                    {
                        axisTicks: {
                            show: true
                        },
                        title: {
                            text: "clicks",
                            style: {
                                fontSize: '20px',
                            },
                        },
                    },
                    {
                        axisTicks: {
                            show: true
                        },
                        opposite: true,
                        title: {
                            text: "Impressions",
                            style: {
                                fontSize: '20px',
                            },
                        }
                    }
                ],
            },
            colors: ["#FF1654", "#247BA0"],
            series: [
                {
                    name: "Clicks",
                    data: []
                },
                {
                    name: "Impressions",
                    data: []
                }
            ],

        };
    }

    componentDidMount() {
        this.props.emitter.addListener('apply', (datasource, campaign) => {
            let title = "";
            title = this.extractTitle(datasource, campaign);
            console.log(title);
            this.setState({title: title});
            let query = [];
            datasource.forEach((value) => {
                query.push(encodeURIComponent("datasource") + '=' + encodeURIComponent(value.name));
            });
            campaign.forEach((value) => {
                query.push(encodeURIComponent("campaign") + '=' + encodeURIComponent(value.name));
            });

            fetch(DATA_URL + "?" + query.join("&"))
                .then(res => res.json())
                .then(data => {
                    let series = [];
                    let clicks = [];
                    let impressions = [];
                    data.forEach((value, index, array) => {
                        var parts = value.date.split('.');
                        series.push(new Date(parts[2], parts[1], parts[0]).getTime());
                        clicks.push(value.clicks);
                        impressions.push(value.impressions)
                    });
                    this.setState({options: {xaxis: {categories: series}}});
                    this.setState({
                        series: [
                            {
                                name: "Clicks",
                                data: clicks
                            },
                            {
                                name: "Impressions",
                                data: impressions
                            }
                        ]
                    });
                });
        })
    }

    extractTitle(datasource, campaign) {
        let title = "";
        if (datasource.length === 0)
            title = title + "All Datasource";
        else {
            let datasources = [];
            datasource.forEach((value) => {
                datasources.push("\"" + value.name + "\"");
                title = "Datasource " + datasources.join(" and ")
            });
        }
        title = title + ";";
        if (campaign.length === 0)
            title = title + "All Campaigns";
        else {
            let campaigns = [];
            campaign.forEach((value) => {
                campaigns.push("\"" + value.name + "\"");
                title = title + "Campaign " + campaigns.join(" and ")
            });
        }
        return title;
    }

    render() {
        return (
            <div className="chart-panel">
                <div className="chart-title"> {this.state.title}</div>
                <div className="mixed-chart">
                    <Chart
                        options={this.state.options}
                        series={this.state.series}
                        type="line"
                        width="1200"
                    />
                </div>
            </div>
        );
    }
}