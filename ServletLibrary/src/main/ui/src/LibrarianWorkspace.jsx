import { Component } from "react";
import $ from "jquery"
import Greeting from "./Greeting.jsx";

class ManagerWorkspace extends Component {
    async listReqs() {
        const response = await fetch('http://localhost:8080/lab1/librarian',
            {
                method: 'POST',
                redirect: 'follow',
                body: JSON.stringify({
                    action: 'list_requests',
                    id_user: '1'
                })
            });
        let data = await response.json();
        let select = $('#reqs');
        select.empty();
        $.each(data, function (index, item) {
            select.append('<option name="req" data-user="' + item.id_user + "\" data-book=\"" + item.id_book + '">' + item.id_book + ' ' + item.accepted + '</option>');
        });
    }

    async acceptBook() {
        let iduser = $('#reqs').find(":selected").data('user');
        let idbook = $('#reqs').find(":selected").data('book');
        const response = await fetch('http://localhost:8080/lab1/librarian',
            {
                method: 'POST',
                redirect: 'follow',
                body: JSON.stringify({
                    action: 'accept_book',
                    id_user: iduser,
                    id_book: idbook
                })
            });
    }

    async refuseBook() {
        let iduser = $('#reqs').find(":selected").data('user');
        let idbook = $('#reqs').find(":selected").data('book');
        const response = await fetch('http://localhost:8080/lab1/librarian',
            {
                method: 'POST',
                redirect: 'follow',
                body: JSON.stringify({
                    action: 'refuse_book',
                    id_user: iduser,
                    id_book: idbook
                })
            });
        let data = await response.json();
        if (data.status == false) {
            $('#status').text(data.message);
        } else {
            $('#status').text("Success");
        }
    }
    componentDidMount() {
        this.listReqs = this.listReqs.bind(this);
        this.acceptBook = this.acceptBook.bind(this);
        this.refuseBook = this.refuseBook.bind(this);
    }

    render() {
        return (
            <div width="100%">
                <Greeting />
                Librarian
                <table width="%100" align="center" class="grid">
                    <tbody>
                        <tr>
                            <td>
                                <button id="list_reqs" onClick={this.listReqs}>List requests</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <select id="reqs" size="3"></select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <button id="accept_book" onClick={this.acceptBook}>Accept book</button>
                                <button id="refuse_book" onClick={this.refuseBook}>Refuse book</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p id="status"></p>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

        )
    }
}

export default ManagerWorkspace;