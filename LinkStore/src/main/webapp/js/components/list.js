window.LinkBox = React.createClass(
{
	loadLinksFromServer: function()
	{
		$.ajax({
			url: this.props.url,
			dataType: 'json',
			cache: false,
			success: function(data) 
			{
				this.setState({data: data});
			}.bind(this),
			error: function(xhr, status, err)
			{
				console.error(this.props.url, status, err.toString());
			}.bind(this)
		});
	},
	
	getInitialState: function()
	{
		return {data: []};
	},
	
	componentDidMount: function() 
	{
		this.loadLinksFromServer();
		setInterval(this.loadLinksFromServer, this.props.pollInterval);
	},
	
	render: function()
	{
		return (
				<div className="linkBox">
					<LinkList data={this.state.data} />
				</div>
		);
	}
});

window.LinkList = React.createClass(
{
	render: function()
	{
		var linkNodes = this.props.data.map(function(link) {
			return (
					<Link key={link.added} link={link}>
					{link.description}
					</Link>
			);
		});
		
		return (
				<div className="linkList">
				{linkNodes}
				</div>
		);
	}
});

window.Link = React.createClass(
{
	rawMarkup: function() 
	{
		var rawMarkup = marked(this.props.children.toString(), {sanitize: true});
		return { __html: rawMarkup };
	},

	render: function()
	{
		return (
				<div className="link">
					<a className="link-title" href={this.props.link.url}>{this.props.link.title}</a><br/>
					<small className="link-url">{this.props.link.url}</small><br/>
					<span dangerouslySetInnerHTML={this.rawMarkup()} />
				</div>
		);
	}
});
