import * as b from 'bobril'
import * as bs from 'bobrilstrap'

export interface IData {
}

export interface IContext extends b.IBobrilCtx {
    data: IData;
}

export const create = b.createComponent<IData>({
    render(ctx: IContext, me: b.IBobrilNode) {
        me.children = [
            {
                tag: "div",
                className: "container",
                style: {minHeight: 500, width: "70%"},
                children: [
                    //  "Find something"  //TODO horca

                    bs.PageHeader({style: {fontFamily: "Georgia",}}, bs.H2({}, ['Find photo  ', bs.Small({}, ' - according to #')])),

                    bs.Form({style: bs.navStyles.navbarForm}, [
                        bs.FormGroup({}, bs.InputText({style: {minWidth: 400}, placeholder: '#Somethig' })), '  ',
                        bs.Button({ label: 'Search' })
                    ])
                ]
            }
        ];
    }
});