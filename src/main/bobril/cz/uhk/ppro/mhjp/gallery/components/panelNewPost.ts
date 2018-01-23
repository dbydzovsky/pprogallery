import * as b from 'bobril'
import * as bs from 'bobrilStrap'


export interface IData {
}

export interface IContext extends b.IBobrilCtx {
    data: IData;
}

export const create = b.createComponent<IData>({
    render(ctx: IContext, me: b.IBobrilNode) {
        me.className = "container";
        me.style = {
            minHeight: 600, width: "70%", backgroundColor: "", marginTop: 20,
        };
        me.children = [
            bs.PageHeader({style:{fontFamily: "Georgia",}}, bs.H2({}, ['New post ', bs.Small({}, ' - choose your galery')])),


            bs.Form({}, [
                bs.FormGroup({}, [
                    bs.Label({ for: 'exampleInputEmail1' }, 'Email address'),
                    bs.InputText({ id: 'exampleInputEmail1', type: bs.InputTextType.Email, placeholder: 'Email' })
                ]),
                bs.FormGroup({}, [
                    bs.Label({ for: 'exampleInputPassword1' }, 'Password'),
                    bs.InputText({ id: 'exampleInputPassword1', type: bs.InputTextType.Password, placeholder: 'Password' })
                ]),
                bs.FormGroup({}, [
                    bs.Label({ for: 'exampleInputTypeahead1' }, 'Typeahead'),
                    bs.InputText({
                        id: 'exampleInputTypeahead1',
                        type: bs.InputTextType.Text,
                        placeholder: 'Write to search',

                    })
                ]),
                bs.FormGroup({}, [
                    bs.Label({ for: 'exampleInputFile' }, 'File input'),
                    bs.E({ tag: 'input', attrs: { type: 'file', id: 'exampleInputFile' } }),
                    bs.HelpText({}, 'Example block-level help text here.')
                ]),
                bs.Checkbox({ label: { title: 'Check me out' }, inputCheckbox: {} }),
                bs.Button({ label: 'Submit', onClick: () => alert('Clicked!') })
            ])




        ]

    }


});