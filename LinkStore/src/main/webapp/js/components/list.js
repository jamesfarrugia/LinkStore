window.LinkBox = React.createClass(
{
	loadLinksFromServer: function()
	{
		if (window.store.searching)
		{
			this.setState({data: []});
			return;
		}
		
		$.ajax({
			url: this.props.url,
			data: {max: 5},
			dataType: 'json',
			cache: false,
			success: function(data) 
			{
				if (window.store.searching)
				{
					this.setState({data: []});
					return;
				}
				
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
		// Done only when not searching.  We'll put some flag, but we should get
		// some flux going.
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
		var rawMarkup = marked(this.props.children?this.props.children.toString():"", {sanitize: true});
		return { __html: rawMarkup };
	},

	render: function()
	{
		var date = new Date(this.props.link.added);
		var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", 
					"Sep", "Oct", "Nov", "Dec"];
		
		return (
				<div className="link">
					<a className="link-title" 
						href={this.props.link.url}>
						{this.props.link.title}
					</a>
					<br/>
					<small className="link-url">
						<span className="link-full-url">
							<nobr>{this.props.link.url}</nobr>
						&nbsp;|&nbsp;Added&nbsp;
							{date.getDate()}&nbsp;
							{months[date.getMonth()]},&nbsp;
							{date.getFullYear()}
						</span>
					</small>
					<span dangerouslySetInnerHTML={this.rawMarkup()} />
				</div>
		);
	}
});
